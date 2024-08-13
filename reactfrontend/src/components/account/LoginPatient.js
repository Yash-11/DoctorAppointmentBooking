import React, { useState } from 'react';
import axios from 'axios';
import { useUser } from '../context/UserContext';
import { useNavigate, Link } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { setUser } = useUser();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const params = new URLSearchParams();
    let hj = username + ":PATIENT";
    params.append('email', username);
    params.append('password', password);

    try {
      const response = await axios.post('http://localhost:8080/login', params);
      console.log(response.data);
      response.data.password = password;
      setUser(response.data);
      // const ree = await axios.get('http://localhost:8080/doctors?specialization=ENT&city=delhi');
      // console.log(ree.data);
      navigate('/form');
    } catch (error) {
      console.error('There was an error!', error);
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Login</h2>
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
        <p>
          Are you a doctor? <Link to="/login_doctor">Login here</Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
