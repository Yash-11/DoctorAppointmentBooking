// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import About from './About';
// import LoginPatient from './LoginPatient';
import Login from './LoginPatient';
import DoctorLogin from './LoginDoctor';
import DoctorAppointmentForm from './DoctorAppointmentForm';
import DoctorList from './DoctorList';
import AppointmentPage from './AppointmentPage';
import { UserProvider } from './UserContext';
import AppointmentBooked from './AppointmentBooked';
import AuthRoute from './AuthRoute';
import PatientRegister from './PatientRegister'
import DoctorRegister from './DoctorRegister';
import PaymentWrapper from './PaymentWrapper';

function App() {
  return (

<UserProvider>
<Router>
<div>
  {/* <h1>Doctor Appointment Booking</h1> */}
  <Routes>
    <Route path="/" element={<Navigate to="/form" />} />
    <Route path="/login" element={<Login />} />
    <Route path="/about" element={<About />} />
    <Route path="/login_doctor" element={<DoctorLogin />} />
    <Route path="/register_patient" element={<PatientRegister />} />
    <Route path="/register_doctor" element={<DoctorRegister />} />
    <Route path="/form" element={<DoctorAppointmentForm />} />
    <Route path="/payment" element={<PaymentWrapper />} />
    <Route path="/doctors" element={<AuthRoute role="ROLE_PATIENT"><DoctorList /></AuthRoute>} />
    <Route path="/appointment/:doctorId" element={<AuthRoute role="ROLE_PATIENT"><AppointmentPage /></AuthRoute>} />
    <Route path="/appointment-booked" element={<AuthRoute role="ROLE_PATIENT"><AppointmentBooked /></AuthRoute>} />
  </Routes>
</div>
</Router>
</UserProvider>
  );
}

export default App;
