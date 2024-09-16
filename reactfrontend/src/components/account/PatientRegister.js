import React, { useState } from 'react';
import axios from 'axios';
import { useUser } from '../context/UserContext';
import useLogout from '../hooks/useLogout';

import { useNavigate, Link, Navigate } from 'react-router-dom';
import './PatientRegister.css';
import Navbar from '../navbar/Navbar';

const PatientRegister = () => {
  const [name, setName] = useState('');
  const [age, setAge] = useState('');
  const [email, setEmail] = useState('');
  const { user, setUser } = useUser();
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const patientData = {
      name,
      age: parseInt(age),
      email,
      password,
    };

    try {
      await axios.post(`${process.env.REACT_APP_API_BASE_URL}/register_patient`, patientData);
      alert('Registration successful!');
      navigate('/form')
    } catch (error) {
      console.error('There was an error!', error);
      alert('Registration failed. Please try again.');
    }
  };

  const handleLogout = useLogout();

  return (
    <div>
      <Navbar handleLogout={handleLogout} />

      <h2 className='h2heading mt-4'>Register</h2>
      <hr className="custom-hr mt-4"></hr>

      <div className="register-container">
        <div className="login-box">
          {/* <h2>Patient Registration</h2> */}
          <form onSubmit={handleSubmit}>
            <div>
              <label >Name:</label>
              <input
                type="text"
                id="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>
            <div>
              <label >Age:</label>
              <input
                type="number"
                id="age"
                value={age}
                onChange={(e) => setAge(e.target.value)}
                required
              />
            </div>
            <div>
              <label >Email:</label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div>
              <label >Password:</label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary">Register</button>
          </form>
          <p>
            Are you a doctor? <Link to="/register_doctor">Register here</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default PatientRegister;
