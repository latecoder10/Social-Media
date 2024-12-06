import {
    Avatar,
    Card,
    CardActions,
    CardContent,
    CardHeader,
    CardMedia,
    Divider,
    IconButton,
    Typography,
  } from "@mui/material";
  import React, { useState, useEffect } from "react";
  import MoreVertIcon from "@mui/icons-material/MoreVert";
  import { red } from "@mui/material/colors";
  import ThumbUpOffAltIcon from "@mui/icons-material/ThumbUpOffAlt";
  import ThumbUpIcon from "@mui/icons-material/ThumbUp";
  import ShareIcon from "@mui/icons-material/Share";
  import ChatIcon from "@mui/icons-material/Chat";
  import BookmarkAddIcon from "@mui/icons-material/BookmarkAdd";
  import BookmarkIcon from "@mui/icons-material/Bookmark";
  import { useDispatch, useSelector } from "react-redux";
  import { createCommentAction, getCommentsForPostAction } from "../../Redux/Comment/comment.action"; // Adjust your imports accordingly
  
  const PostCard = ({ item }) => {
    const [showComments, setShowComments] = useState(false);
    const [commentText, setCommentText] = useState("");
    const [visibleCommentsCount, setVisibleCommentsCount] = useState(5);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const dispatch = useDispatch();
    const comments = useSelector((state) => state.comments[item.id] || []); // Use selector to get comments from Redux state
    console.log("Comments for post ID:", item.id, "Comments:", comments);
    const handleShowComments = () => setShowComments(!showComments);
  
    const handleCreateComment = async () => {
      if (commentText.trim()) {
        const commentData = {
          postId: item.id,
          content: commentText,
        };
  
        setIsSubmitting(true);
  
        try {
          const response = await dispatch(createCommentAction(commentData));
          if (response) {
            setCommentText("");
          } else {
            console.error("Comment creation failed. No payload returned.");
          }
        } catch (error) {
          console.error("Error creating comment:", error);
        } finally {
          setIsSubmitting(false);
        }
      }
    };
  
    const loadMoreComments = () => {
      setVisibleCommentsCount((prevCount) => prevCount + 5);
    };
  
    // Fetch comments when the component mounts or when the item changes
    useEffect(() => {
      if (showComments) {
        dispatch(getCommentsForPostAction(item.id)); // Fetch comments for the post
      }
    }, [showComments, item.id, dispatch]);
  
    return (
      <Card>
        <CardHeader
          avatar={
            <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
              R
            </Avatar>
          }
          action={
            <IconButton aria-label="settings">
              <MoreVertIcon />
            </IconButton>
          }
          title={`${item.user.firstName} ${item.user.lastName}`}
          subheader={`@${item.user.firstName.toLowerCase()}_${item.user.lastName.toLowerCase()}`}
        />
        <CardMedia
          component="img"
          height="194"
          image={item.image}
          alt="Post image"
        />
        <CardContent>
          <Typography variant="body2" sx={{ color: "text.secondary" }}>
            {item.caption}
          </Typography>
        </CardContent>
        <CardActions className="flex justify-between" disableSpacing>
          <div>
            <IconButton>
              {true ? <ThumbUpIcon /> : <ThumbUpOffAltIcon />}
            </IconButton>
            <IconButton>{<ShareIcon />}</IconButton>
            <IconButton onClick={handleShowComments}>{<ChatIcon />}</IconButton>
          </div>
          <div>
            <IconButton>
              {true ? <BookmarkAddIcon /> : <BookmarkIcon />}
            </IconButton>
          </div>
        </CardActions>
        {showComments && (
          <section>
            <div className="flex items-center space-x-5 mx-3 my-5">
              <Avatar sx={{}} />
              <input
                value={commentText}
                onChange={(e) => setCommentText(e.target.value)}
                onKeyPress={(e) => {
                  if (e.key === "Enter" && !isSubmitting) {
                    handleCreateComment();
                  }
                }}
                className="w-full outline-none bg-transparent border border-[#3b4054] rounded-full px-5 py-2"
                type="text"
                placeholder="Write your comments"
                disabled={isSubmitting}
              />
            </div>
            <Divider />
            <div className="mx-3 space-y-2 my-5 text-xs">
              {comments.slice(0, visibleCommentsCount).map((comment, index) => (
                <div key={index} className="flex justify-between items-center">
                  <div className="flex items-center space-x-5">
                    <Avatar sx={{ height: "2rem", width: "2rem", fontSize: ".8rem" }} />
                    <p>{comment.content}</p>
                  </div>
                </div>
              ))}
              {visibleCommentsCount < comments.length && (
                <button
                  onClick={loadMoreComments}
                  className="text-blue-500 hover:underline"
                >
                  Load more comments
                </button>
              )}
            </div>
          </section>
        )}
      </Card>
    );
  };
  
  export default PostCard;
  