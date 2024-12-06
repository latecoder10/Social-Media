import { RadioGroup,TextField, Button, FormControlLabel, Radio } from "@mui/material";
import { ErrorMessage, Field, Formik } from "formik";
import React, { useState } from "react";
import { Form } from "formik"; // Correct import for Form
import * as Yup from "yup";
import { useDispatch } from "react-redux";
import { registerUserAction } from "../../Redux/Auth/auth.action";


// Initial values for the form
const initialValues = {firstName:"",lastName:"", email: "", password: "",gender:"" };

// Validation schema using Yup
const validationSchema = Yup.object({
  email: Yup.string().email("Invalid email").required("Email is Required"),
  password: Yup.string()
    .min(6, "Password must be at least 6 characters")
    .required("Password is Required"),
});

const Register = () => {
  const [gender, setGender] = useState("");
  const dispatch=useDispatch();
  // Handle form submission
  // const [formValue,setFormValue]=useState();
  const handleSubmit = (values) => {
    values.gender=gender;
    console.log("Form values:", values);
    dispatch(registerUserAction({data:values}))
  };
  const handleChange = (event) => {
    setGender(event.target.value);
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
                  name="firstName"
                  placeholder="First Name"
                  type="text"
                  variant="outlined"
                  fullWidth // Correct prop for full width
                />
                <ErrorMessage
                  name="firstName"
                  component={"div"}
                  className="text-red-500"
                />
              </div>
              <div>
                <Field
                  as={TextField}
                  name="lastName"
                  placeholder="Last Name"
                  type="text"
                  variant="outlined"
                  fullWidth // Correct prop for full width
                />
                <ErrorMessage
                  name="lastName"
                  component={"div"}
                  className="text-red-500"
                />
              </div>

              <div>
                <Field
                  as={TextField}
                  name="email"
                  placeholder="Email"
                  type="email"
                  variant="outlined"
                  fullWidth // Correct prop for full width
                />
                <ErrorMessage
                  name="email"
                  component={"div"}
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
                  component={"div"}
                  className="text-red-500"
                />
              </div>

              <RadioGroup
                row
                aria-label="gender"
                name="gender"
                onChange={handleChange}
              >
                <FormControlLabel
                  value="female"
                  control={<Radio />}
                  label="Female"
                />
                <FormControlLabel
                  value="male"
                  control={<Radio />}
                  label="Male"
                />
                <ErrorMessage
                  name="gender"
                  component={"div"}
                  className="text-red-500"
                />
              </RadioGroup>
            </div>
            <Button
              sx={{ padding: ".8rem 0rem" }}
              fullWidth
              type="submit"
              variant="contained"
              color="primary"
            >
              Register
            </Button>
          </Form>
        )}
      </Formik>
    </>
  );
};

export default Register;
