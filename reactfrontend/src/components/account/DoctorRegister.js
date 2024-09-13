import React, { useState } from 'react';
import axios from 'axios';
import { useUser } from '../context/UserContext';
import { useNavigate, Link } from 'react-router-dom';
import useLogout from '../hooks/useLogout';
import './PatientRegister.css';
import Navbar from '../navbar/Navbar';

const DoctorRegister = () => {
  const [name, setName] = useState('');
  const [age, setAge] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { user, setUser } = useUser();


  const handleSubmit = async (e) => {
    e.preventDefault();

    const patientData = {
      name,
      age: parseInt(age),
      email,
      password
    };

    try {
      const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/register_patient`, patientData);
      alert('Registration successful!');
      // Optionally, navigate to a different page or reset the form
    } catch (error) {
      console.error('There was an error!', error);
      alert('Registration failed. Please try again.');
    }
  };

  const handleLogout = useLogout(setUser);

  return (
    <div>
      <Navbar user={user} handleLogout={handleLogout} />
      <div className="register-container">
        <div className="register-box">
          <h2>Doctor Registration</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="name">Name:</label>
              <input
                type="text"
                id="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="age">Age:</label>
              <input
                type="number"
                id="age"
                value={age}
                onChange={(e) => setAge(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="email">Email:</label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="password">Password:</label>
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
        </div>
      </div>
    </div>
  );
};

export default DoctorRegister;
