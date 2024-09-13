import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useUser } from '../context/UserContext';
import { Link } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
import './AppointmentBooked.css';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';


const AppointmentBooked = () => {
  const [appointments, setAppointments] = useState([]);
  const { user, setUser } = useUser();
  const patientId = user.id; // Ensure this is correctly fetching the patient ID

  const handleLogout = useLogout(setUser);

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/appointments/patient/${patientId}`, 
          {headers: {
            "Authorization": "Bearer "+`${user.jwt}`
       }});
        console.log(response.data);
        setAppointments(response.data);
      } catch (error) {
        console.error('Error fetching appointments:', error);
      }
    };

    fetchAppointments();
  }, [patientId]);

  return (
    <div className="container">
      <Navbar user={user} handleLogout={handleLogout} />
    <div className="container mt-5">
      <h2 className="text-center mb-4">Your Appointments</h2>
      {appointments.length > 0 ? (
        <div className="list-group">
          {appointments.map(appointment => (
            <div key={appointment.id} className="list-group-item">
              <p><strong>Doctor:</strong> {appointment.doctorName}</p>
              {/* <p><strong>Date:</strong> {new Date(appointment.date).toLocaleString()}</p> */}
              <p><strong>Date:</strong> {appointment.date}</p>
              <p><strong>Date:</strong> {appointment.time}</p>
              <p><strong>Patient:</strong> {appointment.patientName}</p>
            </div>
          ))}
        </div>
      ) : (
        <p className="text-center">No appointments found.</p>
      )}
    </div>

  </div>
  );
};

export default AppointmentBooked;
