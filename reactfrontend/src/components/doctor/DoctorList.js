import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate, Link } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
import './DoctorList.css';
import { useUser } from '../context/UserContext';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';

const DoctorList = () => {
  const [doctors, setDoctors] = useState([]);
  const location = useLocation();
  const navigate = useNavigate();
  const { user, setUser } = useUser();

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const specialization = queryParams.get('specialization');
    const city = queryParams.get('city');

    // Fetch doctors from the backend API based on specialization and city
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/doctors?specialization=${specialization}&city=${city}`,
      {
        headers: {
          "Authorization": 'Bearer ' + localStorage.getItem('token')
        }
      })
      .then(response => {
        setDoctors(response.data);
      })
      .catch(error => {
        navigate('/login');
        console.error('Error fetching doctors:', error);
      });
  }, [location]);

  const handleDoctorClick = (doctorId) => {
    navigate(`/appointment/${doctorId}`);
  };

  const handleLogout = useLogout();

  return (

    <div>
      <Navbar handleLogout={handleLogout} />

      <div className="container mt-5">
        <h1 className="text-center mb-4 h2heading">Doctors</h1>
        <hr className="custom-hr mt-3 mb-3"></hr>


        <div className="row ">
          {doctors.map(doctor => (
            <div key={doctor.id} className="col-md-4 mb-4 mt-4">
              <div className="card">
                <div className="card-content">

                  <div className="card-image">
                    <img className='img-fluid rounded-start' src={require('../../assets/profile.png')} alt="Doctor" />
                  </div>
                  <div className="card-body">
                    <h5 className="card-title"><Link className='' to={`/doctor/${doctor.id}`}>{doctor.name}</Link></h5>
                    
                    <p className="card-text">Specialization: {doctor.specializations[0]}</p>
                    <p className="card-text">City: {doctor.cityName}</p>

                  </div>
                  {/* <div className="col-md-8">
                  </div> */}
                  

                </div>
                <button
                    className="btn btn-primary"
                    onClick={() => handleDoctorClick(doctor.id)}>
                    Book Appointment
                  </button>
              </div>
            </div>
          ))}
        </div>
      </div>

    </div>
  );
};

export default DoctorList;
