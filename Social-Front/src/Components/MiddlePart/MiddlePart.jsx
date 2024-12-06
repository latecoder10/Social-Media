import { Avatar, Card, IconButton } from "@mui/material";
import React, { useEffect, useState } from "react";
import GroupAddIcon from "@mui/icons-material/GroupAdd";
import StoryCircle from "./StoryCircle";
import PhotoIcon from '@mui/icons-material/Photo';
import VideocamIcon from '@mui/icons-material/Videocam';
import ArticleIcon from '@mui/icons-material/Article';
import PostCard from "../Post/PostCard";
import CreatePostModal from "../CreatePost/CreatePostModal";
import { useDispatch, useSelector } from "react-redux";
import { getAllPostAction } from "../../Redux/Post/post.action";

const story = [1, 1, 1, 1, 1, 1];

const MiddlePart = () => {
  const dispatch = useDispatch();
  const { posts } = useSelector(store => store.post); // Adjust based on Redux structure
  const [openCreatePostModal, setOpenCreatePostModal] = useState(false);

  // Function to open the modal
  const handleOpenCreatePostModal = () => {
    console.log("Opening Post Modal...");
    setOpenCreatePostModal(true);
  };

  // Function to close the modal
  const handleClose = () => {
    setOpenCreatePostModal(false);
  };

  useEffect(() => {
    dispatch(getAllPostAction());
  }, [dispatch]); // Correctly set the dependency array

  return (
    <div className="px-20">
      <section className="flex items-center p-5 rounded-b-md">
        <div className="flex flex-col items-center mr-4 cursor-pointer">
          <Avatar sx={{ width: "5rem", height: "5rem" }}>
            <GroupAddIcon sx={{ fontSize: "3rem" }} />
          </Avatar>
          <p>New</p>
        </div>
        {story.map((item, index) => (
          <StoryCircle key={index} />
        ))}
      </section>
      
      <Card className="p-5 mt-5">
        <div className="flex justify-between">
          <Avatar />
          <input
            onClick={handleOpenCreatePostModal}
            readOnly
            className="outline-none w-[90%] bg-slate-100 rounded-full px-5 bg-transparent border-[#3b4054] border"
            type="text"
          />
        </div>
        
        <div className="flex justify-center space-x-9 mt-5">
          <div className="flex items-center">
            <IconButton color="primary" onClick={handleOpenCreatePostModal}>
              <PhotoIcon />
            </IconButton>
            <span>Media</span>
          </div>
          <div className="flex items-center">
            <IconButton color="primary" onClick={handleOpenCreatePostModal}>
              <VideocamIcon />
            </IconButton>
            <span>Video</span>
          </div>
          <div className="flex items-center">
            <IconButton color="primary" onClick={handleOpenCreatePostModal}>
              <ArticleIcon />
            </IconButton>
            <span>Write Article</span>
          </div>
        </div>
      </Card>
      
      <div className="mt-5 space-y-5">
        {posts && posts.map((item, index) => (
          <PostCard key={index} item={item}/>
        ))}
      </div>
      
      <CreatePostModal handleClose={handleClose} open={openCreatePostModal} />
    </div>
  );
};

export default MiddlePart;
