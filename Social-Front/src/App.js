import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Authentication from './pages/Authentication/Authentication';
import HomePage from './pages/HomePage/HomePage';
import Message from './pages/Message/Message';
import { useDispatch, useSelector } from 'react-redux';
import { store } from './Redux/store';
import { useEffect } from 'react';
import { getprofileAction } from './Redux/Auth/auth.action';

function App() {
  const dispatch=useDispatch()
  const {auth}=useSelector(store=>store);
  const jwt=localStorage.getItem("jwt");
  useEffect(()=>{
    dispatch(getprofileAction(jwt))
  },[jwt])
  return (

      <div className="">
        <Routes>
          <Route path="/*" element={auth.user?<HomePage />:<Authentication />} />
          <Route path="/message" element={<Message />} />
          <Route path="/*" element={<Authentication />} />
          {/* Add more routes as needed */}
        </Routes>
      </div>
    
  );
}

export default App;
