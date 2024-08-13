import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useUser } from '../context/UserContext';
// import 'bootstrap/dist/css/bootstrap.min.css';
import './AppointmentBooked.css';

const AppointmentBooked = () => {
  const [appointments, setAppointments] = useState([]);
  const { user } = useUser();
  const patientId = user.id; // Ensure this is correctly fetching the patient ID

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        const response = await axios.get(`/appointments/patient/${patientId}`, { auth: {
          username: user.email,
          password: user.password
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
  );
};

export default AppointmentBooked;
