// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
// import LoginPatient from './LoginPatient';
import Login from './components/account/LoginPatient';
import DoctorLogin from './components/account/LoginDoctor';
import DoctorAppointmentForm from './components/home/DoctorAppointmentForm';
import DoctorList from './components/doctor/DoctorList';
import AppointmentPage from './components/appointment/AppointmentPage';
import { UserProvider } from './components/context/UserContext';
import AppointmentBooked from './components/appointment/AppointmentBooked';
import AuthRoute from './components/authroute/AuthRoute';
import PatientRegister from './components/account/PatientRegister'
import DoctorRegister from './components/account/DoctorRegister';
import PaymentWrapper from './components/payment/PaymentWrapper';
import DoctorProfile from './components/doctor/DoctorProfile';

function App() {
  return (

<UserProvider>
<Router>
<div>
  {/* <h1>Doctor Appointment Booking</h1> */}
  <Routes>
    <Route path="/" element={<Navigate to="/form" />} />
    <Route path="/login" element={<Login />} />
    <Route path="/login_doctor" element={<DoctorLogin />} />
    <Route path="/register_patient" element={<PatientRegister />} />
    <Route path="/register_doctor" element={<DoctorRegister />} />
    <Route path="/form" element={<DoctorAppointmentForm />} />
    <Route path="/payment" element={<PaymentWrapper />} />
    <Route path="/doctors" element={<DoctorList />} />
    <Route path="/doctor/:id" element={<DoctorProfile />} />
    <Route path="/appointment/:doctorId" element={<AuthRoute role="ROLE_PATIENT"><AppointmentPage /></AuthRoute>} />
    <Route path="/appointment-booked" element={<AuthRoute role="ROLE_PATIENT"><AppointmentBooked /></AuthRoute>} />
  </Routes>
</div>
</Router>
</UserProvider>
  );
}

export default App;
