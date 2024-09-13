import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = ({ user, handleLogout }) => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light container-fluid">
      <Link className="navbar-brand" to="/">
        <img
          src={require('../../assets/logo.png')}
          alt="MediLink Logo"
          style={{ height: '50px' }}
        />
      </Link>

      <div className="collapse navbar-collapse">
        <ul className="navbar-nav ml-auto">
          {!user ? (
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
              <button className="btn btn-link nav-link" onClick={handleLogout}>
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
