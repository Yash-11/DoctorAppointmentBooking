import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate, Link } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
import './DoctorList.css';
import { useUser } from '../context/UserContext';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';
import Badge from 'react-bootstrap/Badge';

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
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/doctors?specialization=${specialization}&city=${city}`)
      .then(response => {
        setDoctors(response.data);
      })
      .catch(error => {
        // navigate('/login');
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

      <div className="mx-auto px-4 py-8">
        <h1 className="text-center mb-4 h2heading text-4xl">Doctors</h1>
        <hr className="custom-hr mt-3 mb-3"></hr>


        <div className="row ">
          {doctors.map((doctor, id) => (
            <div key={id} className="col-md-4 mb-4 mt-4">
              <div className="card h-100">
                <div className="card-content flex-row h-100">

                  <div className="card-image relative flex shrink-0 overflow-hidden w-32 h-32">
                    <img className=' w-full h-full rounded-full object-cover' src={require('../../assets/profile.png')} alt="Doctor" />
                  </div>
                  <div className="card-body ">
                    <div className="card-title text-xl font-medium"><Link className='' to={`/doctor/${doctor.id}`}>Dr. {doctor.name}</Link></div>
                    <div className='flex flex-wrap gap-2 mb-1'>
                      {doctor.services.map((service, _id) => {
                        return <Badge key={_id} bg="dark" text="light">{service}</Badge>
                      })}
                    </div>
                    {/* <p className="card-text">Specialization: {doctor.specializations[0]}</p> */}
                    <p className="card-text">{doctor.address}</p>

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
