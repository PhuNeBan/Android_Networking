import './App.css';
import React, { useState } from 'react';
import {
  BrowserRouter as Router, Route,
  Routes, Navigate, Outlet
} from 'react-router-dom';

import List from './components/News/List';
import Add from './components/News/Add';
import Update from './components/News/Update';
import Login from './components/User/Login';
import ListTopics from './components/Topics/ListTopics';
import AddTopic from './components/Topics/AddTopic';
import UpdateTopic from './components/Topics/UpdateTopic';
import ResetPassWord from './components/User/ResetPassWord';

function App() {

  // get user from local storage
  const getUserFromLocalStorage = () => {
    const _user = localStorage.getItem('user');
    if (_user) {
      return JSON.parse(_user);
    }
    return null;
  }

  // set user to local storage
  const setUserToLocalStorage = (_user) => {
    // user is null => remove user from local storage
    if (!_user) {
      localStorage.removeItem('user');
      setUser(null);
      return;
    }
    // user is not null => set user to local storage
    localStorage.setItem('user', JSON.stringify(_user));
    setUser(_user);
  }


  const [user, setUser] = useState(getUserFromLocalStorage());

  // if user is null => redirect to login page
  const ProtectedLayout = () => {
    if (!user) {
      return <Navigate to="/login" />
    }
    return <Outlet />
  };

  // if user is not null => redirect to home page
  const UnProtectedLayout = () => {
    if (user) {
      return <Navigate to="/" />
    }
    return <Outlet />
  };


  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/reset_password" element={<ResetPassWord/>} />
          <Route element={<UnProtectedLayout />} >
            <Route path="/login" element={<Login setUser={setUserToLocalStorage} />} />
          </Route>
          <Route element={<ProtectedLayout />} >
            <Route path="/" element={<List user={user} setUser={setUserToLocalStorage} />} />
            <Route path="/add" element={<Add user={user} />} />
            <Route path="/update/:id" element={<Update user={user} />} />
            <Route path="/list-topics" element={<ListTopics user={user} setUser={setUserToLocalStorage} />} />
            <Route path="/add-topic" element={<AddTopic user={user} />} />
            <Route path="/update-topic/:id" element={<UpdateTopic user={user} />} />
          </Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
