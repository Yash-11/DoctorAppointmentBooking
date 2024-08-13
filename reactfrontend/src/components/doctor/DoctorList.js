import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
import './DoctorList.css';
import { useUser } from '../context/UserContext'; 

const DoctorList = () => {
  const [doctors, setDoctors] = useState([]);
  const location = useLocation();
  const navigate = useNavigate();
  const { user } = useUser();

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const specialization = queryParams.get('specialization');
    const city = queryParams.get('city');

    console.log(user.password);
    let hj = user.email + ":PATIENT";
    console.log(hj);

    // Fetch doctors from the backend API based on specialization and city
    axios.get(`/doctors?specialization=${specialization}&city=${city}`, { auth: {
      username: user.email,
      password: user.password
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

  return (
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
  );
};

export default DoctorList;
