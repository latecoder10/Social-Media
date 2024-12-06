import { Avatar, Box, Button, Card, Tab, Tabs } from "@mui/material";
import React from "react";
import { useParams } from "react-router-dom";
import PostCard from "../Post/PostCard";
import UserReelCard from "../Reels/UserReelCard";
import { useSelector } from "react-redux";
import ProfileModal from "./ProfileModal";

const tabs = [
  { value: "post", name: "Post" },
  { value: "reels", name: "Reels" },
  { value: "saved", name: "SavedPost" },
  { value: "repost", name: "RePost" },
];
const posts = [1, 1, 1, 1, 1];
const reels = [1, 1, 1, 1, 1];
const SavedPost = [1, 1, 1, 1];

const Profile = () => {
  const [open, setOpen] = React.useState(false);
  const handleOpenProfileModal = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const { auth } = useSelector((store) => store);
  const [value, setValue] = React.useState("post");
  const { id } = useParams();

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Card className="my-10 w-[70%]">
      <div className="rounded-md">
        <div className="h-[15rem]">
          <img
            className="w-full h-full rounded-t-md"
            src="https://phlearn.com/wp-content/uploads/2018/06/Change-Remove-Backgrounds-Silhouette-After.jpg"
            alt=""
          />
        </div>
        <div className="px-5 flex justify-between items-start mt-5 h-[5rem]">
          <Avatar
            className="transform -translate-y-24"
            sx={{ width: "10rem", height: "10rem" }}
            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-jLhDQjJ4Hu-tMCFbGDKO_-A2ozkHPheIdQ&s"
          />
          {true ? (
            <Button sx={{ borderRadius: "20px" }} variant="outlined" onClick={handleOpenProfileModal}>
              Edit Profile
            </Button>
          ) : (
            <Button sx={{ borderRadius: "20px" }} variant="outlined">
              Follow
            </Button>
          )}
        </div>
        <div className="p-5">
          <h1 className="py-1 font-bold text-xl">
            {auth.user?.firstName + " " + auth.user?.lastName}
          </h1>
          <p>
            @
            {auth.user?.firstName.toLowerCase() +
              "_" +
              auth.user?.lastName.toLowerCase()}
          </p>

          <div className="flex gap-5 items-center py-3">
            <span>41 post</span>
            <span>91 followers</span>
            <span>5 followings</span>
          </div>
          <div>
            <p>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Laborum
              ea quod et ut, enim doloribus, autem eos porro quibusdam quas
              omnis iusto minima, alias laudantium eum sint delectus repudiandae
              asperiores!
            </p>
          </div>
        </div>
        <section>
          <Box
            sx={{
              maxWidth: { xs: 320, sm: 480 },
              bgcolor: "background.paper",
              borderBottom: 1,
              borderColor: "divider",
            }}
          >
            <Tabs
              value={value}
              onChange={handleChange}
              variant="scrollable"
              scrollButtons="auto"
              aria-label="scrollable auto tabs example"
            >
              {tabs.map((item) => (
                <Tab key={item.value} value={item.value} label={item.name} wrapped />
              ))}
            </Tabs>
          </Box>
          <div className="flex justify-center">
            {value === "post" ? (
              <div className="space-y-5 w-[70%] my-10">
                {posts.map((item, index) => (
                  <div key={index} className="border border-slate-100 rounded-md">
                    <PostCard />
                  </div>
                ))}
              </div>
            ) : value === "reels" ? (
              <div className="flex justify-center flex-wrap gap-2 my-10">
                {reels.map((_, index) => (
                  <UserReelCard key={index} />
                ))}
              </div>
            ) : value === "saved" ? (
              <div className="space-y-5 w-[70%] my-10">
                {SavedPost.map((item, index) => (
                  <div key={index} className="border border-slate-100 rounded-md">
                    <PostCard />
                  </div>
                ))}
              </div>
            ) : (
              "Repost"
            )}
          </div>
        </section>
      </div>
      <section>
        <ProfileModal open={open} handleClose={handleClose} />
      </section>
    </Card>
  );
};

export default Profile;
