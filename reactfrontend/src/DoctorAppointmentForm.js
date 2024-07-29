import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import { useUser } from './UserContext';
// import 'bootstrap/dist/css/bootstrap.min.css';

const DoctorAppointmentForm = () => {
  const [specializations, setSpecializations] = useState([]);
  const [cities, setCities] = useState([]);
  const [selectedSpecialization, setSelectedSpecialization] = useState('');
  const [selectedCity, setSelectedCity] = useState('');
  const { user, setUser } = useUser();
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('/all_specializations')
      .then(response => {
        setSpecializations(response.data);
      })
      .catch(error => {
        console.error('Error fetching specializations:', error);
      });

    axios.get('/cities')
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

  const handleSubmit = (event) => {
    event.preventDefault();
    navigate(`/doctors?specialization=${selectedSpecialization}&city=${selectedCity}`);
  };

  const handleLogout = () => {
    setUser(null);
    navigate('/login');
  };

  return (
    <div className="container">
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <Link className="navbar-brand" to="/">Doctor Appointment Booking</Link>
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/about">About</Link>
            </li>
            {!user ? (
              <><li className="nav-item">
                <Link className="nav-link" to="/login">Login</Link>
              </li><li className="nav-item">
                  <Link className="nav-link" to="/register_patient">Register</Link>
                </li></>
            ) : (
              <li className="nav-item">
                <button className="btn btn-link nav-link" onClick={handleLogout}>Logout</button>
              </li>
            )}
          </ul>
        </div>
      </nav>
      <div className="mt-4">
        <h2>Book a Doctor Appointment</h2>
        <form onSubmit={handleSubmit} className="mt-3">
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
          <button type="submit" className="btn btn-primary">Submit</button>
        </form>
      </div>
    </div>
  );
};

export default DoctorAppointmentForm;
