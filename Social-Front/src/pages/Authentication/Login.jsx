import { TextField, Button } from "@mui/material";
import { ErrorMessage, Field, Formik } from "formik";
import React, { useState } from "react";
import { Form } from "formik"; // Correct import for Form
import * as Yup from "yup";
import { useDispatch } from "react-redux";
import { loginUserAction } from "../../Redux/Auth/auth.action";

// Initial values for the form
const initialValues = { email: "", password: "" };

// Validation schema using Yup
const validationSchema = Yup.object({
  email: Yup.string().email("Invalid email").required("Email is Required"),
  password: Yup.string()
    .min(6, "Password must be at least 6 characters")
    .required("Password is Required"),
});

const Login = () => {
  const [formValue,setFormValue]=useState();
  const dispatch=useDispatch();
  // Handle form submission
  // const [formValue,setFormValue]=useState();
  const handleSubmit = (values) => {
    console.log("Form values:", values);
    dispatch(loginUserAction({data:values}))
  };

  return (
    <>
    <Formik
      initialValues={initialValues}
      validationSchema={validationSchema}
      onSubmit={handleSubmit}
    >
      {() => (
        <Form className="space-y-5">
          <div className="space-y-5">
            <div>
              <Field
                as={TextField}
                name="email"
                placeholder="Email"
                variant="outlined"
                fullWidth // Correct prop for full width
              />
              <ErrorMessage
                name="email"
                component="div"
                className="text-red-500"
              />
            </div>
            <div>
              <Field
                as={TextField}
                name="password"
                placeholder="Password"
                type="password" // Type for password field
                variant="outlined"
                fullWidth // Correct prop for full width
              />
              <ErrorMessage
                name="password"
                component="div"
                className="text-red-500"
              />
            </div>
          </div>
          <Button
            sx={{ padding: ".8rem 0rem" }}
            fullWidth
            type="submit"
            variant="contained"
            color="primary"
          >
            Login
          </Button>
        </Form>
      )}
    </Formik>
    </>
  );
};

export default Login;
