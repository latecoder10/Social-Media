// post.reducer.js
import { 
    CREATE_POST_FAILURE, 
    CREATE_POST_REQUEST, 
    CREATE_POST_SUCCESS, 
    GET_ALL_POST_FAILURE, 
    GET_ALL_POST_REQUEST, 
    GET_ALL_POST_SUCCESS, 
    LIKE_POST_FAILURE, 
    LIKE_POST_REQUEST, 
    LIKE_POST_SUCCESS 
  } from "./post.actionType";
  
  const initialState = {
      post: null,
      loading: false,
      error: null,
      posts: [],
      like: null
  };
  
  export const postReducer = (state = initialState, action) => {
      switch (action.type) {
          case CREATE_POST_REQUEST:
          case GET_ALL_POST_REQUEST:
          case LIKE_POST_REQUEST:
              return { ...state, error: null, loading: true }; // Set loading to true on request
  
          case CREATE_POST_SUCCESS:
              return {
                  ...state, 
                  post: action.payload,
                  posts: [action.payload, ...state.posts],  // Add the new post at the beginning of the posts array
                  loading: false,
                  error: null
              };
  
          case GET_ALL_POST_SUCCESS:
              return {
                  ...state,
                  posts: action.payload, // Set posts to the list retrieved from the server
                  loading: false,
                  error: null
              };
  
          case LIKE_POST_SUCCESS:
              return {
                  ...state,
                  posts: state.posts.map((item) => 
                    item.id === action.payload.id ? { ...item, ...action.payload } : item
                  ), // Update the specific post with new like information
                  loading: false,
                  error: null
              };
  
          case CREATE_POST_FAILURE:
          case GET_ALL_POST_FAILURE:
          case LIKE_POST_FAILURE:
              return { ...state, error: action.payload, loading: false };
  
          default:
              return state;
      }
  };
  