// comment.actions.js
import { api } from "../../Config/api";
import {
    CREATE_COMMENT_REQUEST,
    CREATE_COMMENT_SUCCESS,
    CREATE_COMMENT_FAILURE,
    GET_COMMENTS_REQUEST,
    GET_COMMENTS_SUCCESS,
    GET_COMMENTS_FAILURE,
} from "./comment.actionType";

// Action to create a new comment
// Assuming you have a similar structure for your createCommentAction
export const createCommentAction = (commentData) => async (dispatch) => {
    dispatch({ type: CREATE_COMMENT_REQUEST });

    try {
        const response = await api.post('/api/comments', commentData);
        dispatch({ type: CREATE_COMMENT_SUCCESS, payload: response.data });
        return response.data; // Return the response data
    } catch (error) {
        dispatch({ type: CREATE_COMMENT_FAILURE, payload: error });
        throw error; // Rethrow the error for further handling
    }
};


// Action to fetch comments for a specific post
export const getCommentsForPostAction = (postId, page = 0, size = 10) => async (dispatch) => {
    dispatch({ type: GET_COMMENTS_REQUEST });

    try {
        const { data } = await api.get(`/api/posts/${postId}/comments?page=${page}&size=${size}`); // Ensure this endpoint is correct
        dispatch({ type: GET_COMMENTS_SUCCESS, payload: data });
        console.log("Fetched comments: ", data);
    } catch (error) {
        console.log("Error fetching comments: ", error);
        dispatch({ type: GET_COMMENTS_FAILURE, payload: error.message });
    }
};
