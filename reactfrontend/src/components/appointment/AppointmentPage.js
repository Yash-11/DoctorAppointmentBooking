import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';
import { useUser } from '../context/UserContext'; 
import { useNavigate } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
import './AppointmentPage.css';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';

const AppointmentPage = () => {
  const { doctorId } = useParams();
  const [doctor, setDoctor] = useState(null);
  const [slots, setSlots] = useState([]);
  const { user, setUser } = useUser();
  const navigate = useNavigate();

  const handleLogout = useLogout(setUser);

  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/doctors/${doctorId}`, 
      {headers: {
        "Authorization": "Bearer "+`${user.jwt}`
   }})
      .then(response => {
        setDoctor(response.data);
      })
      .catch(error => {
        console.error('Error fetching doctor details:', error);
      });

    axios.get(`${process.env.REACT_APP_API_BASE_URL}/doctors/${doctorId}/slots`, 
      {headers: {
        "Authorization": "Bearer "+`${user.jwt}`
   }})
      .then(response => {
        setSlots(response.data);
      })
      .catch(error => {
        console.error('Error fetching slots:', error);
      });
  }, [doctorId]);

  const handleSlotClick = async (time, date) => {
    if (!user) {
      alert('Please log in to book an appointment');
      return;
    }

    try {
      const res = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/book`, {
          date: date,
          time: time,
          doctorId: doctorId,
          patientId: user.id // Use the logged-in user's patient ID
        }, 
        {headers: {
            "Authorization": "Bearer "+`${user.jwt}`
        }}
      );      

      const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/confirmAppointment/${res.data.id}`, 
        {headers: {
            "Authorization": "Bearer "+`${user.jwt}`
        }}
      );

      alert(`Appointment booked for ${response.data.date} at ${response.data.time}`);
      navigate('/appointment-booked');
    } catch (error) {
      console.error('Error booking appointment:', error);
      alert('Failed to book appointment');
    }

    // try {
      
    // }catch (error) {
    //   console.error('Error booking appointment:', error);
    //   alert('Failed to book appointment');
    // }
  };

  if (!doctor) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container">
      <Navbar user={user} handleLogout={handleLogout} />

    <div className="container mt-5">
      <div className="card mb-4">
        <div className="card-body">
          <h1 className="card-title">Appointment for {doctor.name}</h1>
          <p className="card-text"><strong>Specialization:</strong> {doctor.specializationName}</p>
          <p className="card-text"><strong>City:</strong> {doctor.cityName}</p>
        </div>
      </div>
      {slots.map(slot => (
        <div key={slot.date} className="card mb-3">
          <div className="card-header">
            <h3>{slot.date}</h3>
          </div>
          <div className="card-body">
            <div className="row">
              {slot.availableTimes.map(ss => (
                <div key={ss} className="col-md-3 mb-2">
                  <button className="btn btn-primary w-100" onClick={() => handleSlotClick(ss, slot.date)}>
                    {ss}
                  </button>
                </div>
              ))}
            </div>
          </div>
        </div>
      ))}
    </div>

    </div>
  );
};

export default AppointmentPage;
