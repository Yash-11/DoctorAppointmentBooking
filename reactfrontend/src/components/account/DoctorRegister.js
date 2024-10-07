import React, { useState, useEffect } from 'react';
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
  const navigate = useNavigate();

  const [specializations, setSpecializations] = useState([]);
  const [cities, setCities] = useState([]);
  const [selectedSpecialization, setSelectedSpecialization] = useState('');
  const [selectedCity, setSelectedCity] = useState('');

  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/all_specializations`)
      .then(response => {
        setSpecializations(response.data);
      })
      .catch(error => {
        console.error('Error fetching specializations:', error);
      });


    axios.get(`${process.env.REACT_APP_API_BASE_URL}/cities`)
      .then(response => {
        setCities(response.data);
      })
      .catch(error => {
        console.error('Error fetching cities:', error);
      });

  }, []);

  const handleSpecializationChange = (event) => {
    setSelectedSpecialization(event.target.value);
  };

  const handleCityChange = (event) => {
    setSelectedCity(event.target.value);
  };


  const handleSubmit = async (e) => {
    e.preventDefault();

    const patientData = {
      name: name,
      specializationName: selectedSpecialization,
      cityName: selectedCity,
      email: email,
      password: password
    };

    try {
      const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/register_doctor`, patientData);
      alert('Registration successful!');
      navigate('/form')
      // Optionally, navigate to a different page or reset the form
    } catch (error) {
      console.error('There was an error!', error);
      alert('Registration failed. Please try again.');
    }
  };

  const handleLogout = useLogout();

  return (
    <div>
      <Navbar handleLogout={handleLogout} />
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
            {/* <div className="form-group">
              <label htmlFor="age">Age:</label>
              <input
                type="number"
                id="age"
                value={age}
                onChange={(e) => setAge(e.target.value)}
                required
              />
            </div> */}
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
              <label htmlFor="specialization">Doctor Specialization:</label>
              <select
                id="specialization"
                className="form-control"
                value={selectedSpecialization}
                onChange={handleSpecializationChange}
                >
                <option value="">Select Specialization</option>
                {specializations.map(specialization => (
                  <option key={specialization.id} value={specialization.name}>
                    {specialization.name}
                  </option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="city">City:</label>
              <select
                id="city"
                className="form-control"
                value={selectedCity}
                onChange={handleCityChange}
              >
                <option value="">Select City</option>
                {cities.map(city => (
                  <option key={city.id} value={city.name}>
                    {city.name}
                  </option>
                ))}
              </select>
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
