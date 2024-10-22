import React from 'react';
import './Navbar.css'
import { Link } from 'react-router-dom';

const Navbar = ({ handleLogout }) => {
  var user=0;
  if (localStorage.getItem('token')) user = 1;
  else user = 0;
  console.log(user);
  
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light container-fluid">
      <Link className="navbar-brand" to="/">
        <img
          src={require('../../assets/logo.png')}
          alt="MediLink Logo"
          style={{ height: '50px' }}
        />
      </Link>

      <div className=" navbar-collapse">
        <ul className="navbar-nav ml-auto">
          {user===0 ? (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/login">
                  Login
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/register_patient">
                  Register
                </Link>
              </li>
            </>
          ) : (
            <li className="nav-item">
              <button className="logout-btn" onClick={handleLogout}>
                Logout
              </button>
            </li>
          )}
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
