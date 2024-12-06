import axios from "axios";
import { api, API_BASE_URL } from "../../Config/api";
import {
    GET_PROFILE_FAILURE,
    GET_PROFILE_REQUEST,
    GET_PROFILE_SUCCESS,
    LOGIN_FAILURE,
    LOGIN_REQUEST,
    LOGIN_SUCCESS,
    REGISTER_FAILURE,
    REGISTER_REQUEST,
    REGISTER_SUCCESS,
    UPDATE_PROFILE_FAILURE,
    UPDATE_PROFILE_REQUEST,
    UPDATE_PROFILE_SUCCESS
} from "./auth.actionType";

// Action for logging in
export const loginUserAction = (loginData) => async (dispatch) => {
    dispatch({ type: LOGIN_REQUEST });
    try {
        const { data } = await axios.post(`${API_BASE_URL}/auth/signin`, loginData.data);
        if (data.token) {
            localStorage.setItem("jwt", data.token); // Correctly store the JWT
            console.log("logged in", data.token);
        }
        console.log("logged in", data);
        dispatch({ type: LOGIN_SUCCESS, payload: data.token });
    } catch (error) {
        console.log("-------------------", error);
        dispatch({ type: LOGIN_FAILURE, payload: error });
    }
}

// Action for registering
export const registerUserAction = (registerData) => async (dispatch) => {
    dispatch({ type: REGISTER_REQUEST }); // Use REGISTER_REQUEST instead of LOGIN_REQUEST
    try {
        const { data } = await axios.post(`${API_BASE_URL}/auth/signup`, registerData.data);
        if (data.token) {
            localStorage.setItem("jwt", data.token); // Correctly store the JWT
        }
        console.log("registered", data);
        dispatch({ type: REGISTER_SUCCESS, payload: data.token }); // Use REGISTER_SUCCESS
    } catch (error) {
        console.log("-------------------", error);
        dispatch({ type: REGISTER_FAILURE, payload: error }); // Use REGISTER_FAILURE
    }
}

// Action for getting profile
export const getprofileAction = (jwt) => async (dispatch) => {
    dispatch({ type: GET_PROFILE_REQUEST });
    try {
        const { data } = await api.get('/api/users/profile'); // api instance handles the token
        dispatch({ type: GET_PROFILE_SUCCESS, payload: data });
        console.log("success----------------",data);
    } catch (error) {
        console.log("error------------",error)
        dispatch({ type: GET_PROFILE_FAILURE, payload: error.response ? error.response.data : error.message });
    }
}

// Action for updating profile
export const updateprofileAction = (reqData) => async (dispatch) => {
    dispatch({ type: UPDATE_PROFILE_REQUEST });
    try {
        const { data } = await api.put(`${API_BASE_URL}/api/users/update`, reqData); // Change to PUT for updating
        console.log("update profile", data);
        dispatch({ type: UPDATE_PROFILE_SUCCESS, payload: data });
    } catch (error) {
        console.log("-------------------", error);
        dispatch({ type: UPDATE_PROFILE_FAILURE, payload: error });
    }
}
