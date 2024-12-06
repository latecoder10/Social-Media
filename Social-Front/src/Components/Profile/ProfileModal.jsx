import * as React from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { useDispatch } from "react-redux";
import { useFormik } from "formik";
import { Avatar, Button, IconButton, TextField } from "@mui/material";
import { updateprofileAction } from "../../Redux/Auth/auth.action";
import CloseFullscreenIcon from "@mui/icons-material/CloseFullscreen";
const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 600,
  bgcolor: "background.paper",

  boxShadow: 24,
  p: 2,
  outline: "none",
  overFlow: "scroll-y",
  borderRadius: 3,
};

export default function ProfileModal({ open, handleClose }) {
  const dispatch = useDispatch();

  const handleSubmit = (values) => {
    console.log("Submitted values:", values);
    // You could dispatch an action here if needed
  };

  const formik = useFormik({
    initialValues: {
      firstName: "",
      lastName: "",
    },
    onSubmit: (values) => {
      console.log("values", values);
      dispatch(updateprofileAction(values));
    },
  });

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Typography id="modal-modal-title" variant="h6" component="h2">
          Profile Information
        </Typography>
        <form onSubmit={formik.handleSubmit}>
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <IconButton onClick={handleClose}>
                <CloseFullscreenIcon />
              </IconButton>
              <p>Edit Profile</p>
            </div>
            <Button type="submit">Save</Button>
          </div>

          <div className="h-[15rem]">
            <img
              className="w-full h-full rounded-t-md"
              src="https://phlearn.com/wp-content/uploads/2018/06/Change-Remove-Backgrounds-Silhouette-After.jpg"
              alt=""
            />
          </div>
          <div className="pl-5">
            <Avatar
              className="transform -translate-y-24"
              sx={{ width: "10rem", height: "10rem" }}
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-jLhDQjJ4Hu-tMCFbGDKO_-A2ozkHPheIdQ&s"
            />
          </div>
          <div className="space-y-3">
            <TextField fullWidth id="firstName" name="firstName" label="First Name" value={formik.values.firstName} onChange={formik.handleChange}/>
            <TextField fullWidth id="lastName" name="lastName" label="Last Name" value={formik.values.lastName} onChange={formik.handleChange}/>
          </div>
        </form>
      </Box>
    </Modal>
  );
}
