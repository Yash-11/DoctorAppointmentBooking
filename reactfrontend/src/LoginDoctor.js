import React, { useState } from 'react';
import axios from 'axios';
import { useUser } from './UserContext';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const DoctorLogin = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { setUser } = useUser();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const params = new URLSearchParams();
    let hj = username + ":DOCTOR"
    params.append('email', hj);
    params.append('password', password);

    try {
      const response = await axios.post('http://localhost:8080/login', params);
      console.log(response.data);
      setUser(response.data);
      navigate('/doctor-dashboard');
    } catch (error) {
      console.error('There was an error!', error);
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Doctor Login</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label>Username: </label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Password: </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
};

export default DoctorLogin;
