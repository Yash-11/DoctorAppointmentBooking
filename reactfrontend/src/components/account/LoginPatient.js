import React, { useState } from 'react';
import axios from 'axios';
import { useUser } from '../context/UserContext';
import { useNavigate, Link } from 'react-router-dom';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';

import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { user, setUser } = useUser();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const params = new URLSearchParams();
    let hj = username + ":PATIENT";
    params.append('email', username);
    params.append('password', password);

    try {

    //   const response = await axios.post('http://localhost:8080/ggg', null, {
    //     headers: {
    //        "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcyNDA2Mjk4MiwiZXhwIjoxNzI0MDk4OTgyfQ.ncoPzAPPhzkMquYNKvyncmefJEZTf6AzwULIeJDZu3k"
    //     }
    //  });
    //   console.log(response.data);

      const patient = {
        username: username,
        password: password
      }

      const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/authenticate`, patient);
      console.log(response.data);


      // const response = await axios.post('http://localhost:8080/login', params);
      // console.log(response.data);
      // response.data.password = password;
      // setUser(response.data);

      localStorage.setItem('token', response.data.jwt);
      localStorage.setItem('role', response.data.role);
      navigate('/form');
    } catch (error) {
      console.error('There was an error!', error);
    }
  };

  const handleLogout = useLogout();

  return (
    <div>
      <Navbar handleLogout={handleLogout} />

      <h2 className='h2heading text-4xl mt-4'>Login</h2>
      <hr className="custom-hr mt-4"></hr>

      <div className="login-containe">
        <div className="login-box">
          {/* <h2>Login</h2> */}
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
    </div>
  );
};

export default Login;
