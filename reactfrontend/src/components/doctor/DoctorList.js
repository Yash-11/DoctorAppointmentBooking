import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate, Link } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
import './DoctorList.css';
import { useUser } from '../context/UserContext'; 
import useLogout from '../hooks/useLogout';

const DoctorList = () => {
  const [doctors, setDoctors] = useState([]);
  const location = useLocation();
  const navigate = useNavigate();
  const { user, setUser } = useUser();

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const specialization = queryParams.get('specialization');
    const city = queryParams.get('city');

    console.log(user.password);
    let hj = user.email + ":PATIENT";
    console.log(hj);

    // Fetch doctors from the backend API based on specialization and city
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/doctors?specialization=${specialization}&city=${city}`, 
      {headers: {
        "Authorization": "Bearer "+`${user.jwt}`
   }})
      .then(response => {
        setDoctors(response.data);
      })
      .catch(error => {
        console.error('Error fetching doctors:', error);
      });
  }, [location]);

  const handleDoctorClick = (doctorId) => {
    navigate(`/appointment/${doctorId}`);
  };

  const handleLogout = useLogout(setUser);

  return (

    <div className="container">
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <Link className="navbar-brand" to="/">
          <img src={require('../../assets/logo.png')} alt="MediLink Logo" style={{ height: '50px' }} />
        </Link>
        
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav ml-auto">
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

    <div className="container mt-5">
      <h1 className="text-center mb-4">Doctors</h1>
      <div className="row">
        {doctors.map(doctor => (
          <div key={doctor.id} className="col-md-4 mb-4">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">{doctor.name}</h5>
                <p className="card-text">Specialization: {doctor.specializationName}</p>
                <p className="card-text">City: {doctor.cityName}</p>
                <button 
                  className="btn btn-primary" 
                  onClick={() => handleDoctorClick(doctor.id)}>
                  Book Appointment
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>

    </div>
  );
};

export default DoctorList;
