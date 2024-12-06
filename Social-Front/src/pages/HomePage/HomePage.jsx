import { Grid } from "@mui/material";
import React, { useEffect } from "react";
import Sidebar from "../../Components/Sidebar/Sidebar";
import { Route, Routes, useLocation } from "react-router-dom";
import MiddlePart from "../../Components/MiddlePart/MiddlePart";
import Reels from "../../Components/Reels/Reels";
import CreateReelsForm from "../../Components/Reels/CreateReelsForm";
import Profile from "../../Components/Profile/Profile";
import HomeRight from "../../Components/HomeRight/HomeRight";
import { useDispatch, useSelector } from "react-redux";
import { getprofileAction } from "../../Redux/Auth/auth.action";

const HomePage = () => {
  const dispatch = useDispatch();
  const location = useLocation();
  const jwt = localStorage.getItem("jwt");

  const { auth } = useSelector(state => state);

  useEffect(() => {
    if (jwt) {
      dispatch(getprofileAction());
    }
  }, [dispatch, jwt]);

  useEffect(() => {
    console.log("auth", auth); // Logs only when auth changes
  }, [auth]);

  return (
    <div className="px-20">
      <Grid container spacing={0}>
        <Grid item xs={0} lg={3}>
          <div className="sticky top-0">
            <Sidebar />
          </div>
        </Grid>
        <Grid
          item
          lg={location.pathname === "/" ? 6 : 9}
          className="px-5 flex justify-center"
          xs={12}
        >
          <Routes>
            <Route path="/" element={<MiddlePart />} />
            <Route path="/reels" element={<Reels />} />
            <Route path="/create-reels" element={<CreateReelsForm />} />
            <Route path="/profile/:id" element={<Profile />} />
          </Routes>
        </Grid>
        {location.pathname === "/" && (
          <Grid item lg={3} className="relative">
            <div className="sticky top-0 w-full">
              <HomeRight />
            </div>
          </Grid>
        )}
      </Grid>
    </div>
  );
};

export default HomePage;
