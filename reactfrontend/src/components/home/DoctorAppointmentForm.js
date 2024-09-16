import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import { useUser } from '../context/UserContext';
// import 'bootstrap/dist/css/bootstrap.min.css';
import './DoctorAppointmentForm.css'
import { Carousel } from 'react-bootstrap';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';



const DoctorAppointmentForm = () => {
  const [specializations, setSpecializations] = useState([]);
  const [cities, setCities] = useState([]);
  const [selectedSpecialization, setSelectedSpecialization] = useState('');
  const [selectedCity, setSelectedCity] = useState('');
  const { user, setUser } = useUser();
  const navigate = useNavigate();

  const reviews = [
    { author: 'John Doe', content: 'Great service! Highly recommend.' },
    { author: 'Jane Smith', content: 'Very professional and quick.' },
    { author: 'Bob Johnson', content: 'Easy to book an appointment.' },
    { author: 'Alice Brown', content: 'Excellent experience overall.' },
    { author: 'Michael Lee', content: 'Doctors are very knowledgeable and friendly.' }
  ];

  const [currentReviewIndex, setCurrentReviewIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentReviewIndex((prevIndex) => (prevIndex + 1) % reviews.length);
    }, 3000); // Change review every 3 seconds

    return () => clearInterval(interval);
  }, [reviews.length]);

  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/all_specializations`)
      .then(response => {
        setSpecializations(response.data);
      })
      .catch(error => {
        console.error('Error fetching specializations:', error);
      });


    axios.get(`${process.env.REACT_APP_API_BASE_URL}/cities`)
      .then(response => {
        setCities(response.data);
      })
      .catch(error => {
        console.error('Error fetching cities:', error);
      });

  }, []);

  const handleSpecializationChange = (event) => {
    setSelectedSpecialization(event.target.value);
  };

  const handleCityChange = (event) => {
    setSelectedCity(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    navigate(`/doctors?specialization=${selectedSpecialization}&city=${selectedCity}`);
  };

  // const handleLogout = () => {
  //   setUser(null);
  //   navigate('/login');
  // };
  const handleLogout = useLogout();

  return (
    <div>

      <Navbar handleLogout={handleLogout} />

      {/* <img src="https://via.placeholder.com/300" alt="Placeholder Image"> */}
            {/* <img src="./homeimg.jpg" alt="MediLink Logo" style={{ height: '50px' }} /> */}

      <div className="coainnt mt-4" >

      <div className="mt-4 ggg rrr">
        <h2 className='h2heading'>Find a Doctor</h2>
        <form onSubmit={handleSubmit} className="mt-3">
          <div className="form-group">
            <label htmlFor="specialization">Doctor Specialization:</label>
            <select
              id="specialization"
              className="form-control"
              value={selectedSpecialization}
              onChange={handleSpecializationChange}
              >
              <option value="">Select Specialization</option>
              {specializations.map(specialization => (
                <option key={specialization.id} value={specialization.name}>
                  {specialization.name}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="city">City:</label>
            <select
              id="city"
              className="form-control"
              value={selectedCity}
              onChange={handleCityChange}
            >
              <option value="">Select City</option>
              {cities.map(city => (
                <option key={city.id} value={city.name}>
                  {city.name}
                </option>
              ))}
            </select>
          </div>
          <button type="submit" className="btn btn-primary submit-button">Submit</button>
        </form>
      </div>

      <div className='mt-4 ggg '>
        {/* <p> </p> */}
        <img className='image-container' src="./homeimg.jpg" alt="MediLink Logo"/>
      </div>


      </div>


      <hr className="custom-hr mt-4"></hr>

      <div className="mt-4 Carousel-container">
        <div>
          <h2 className='h2heading mb-3'>What Our Patients Say</h2>
          
          <Carousel indicators={true} controls={true}>
            {reviews.map((review, index) => (
              <Carousel.Item key={index}>
                <div className="card testimonial-card">
                  <div className="card-body testimonial">
                    <blockquote className="blockquote mb-0">
                      <p>{review.content}</p>
                      <footer className="blockquote-footer">{review.author}</footer>
                    </blockquote>
                  </div>
                </div>
              </Carousel.Item>
            ))}
          </Carousel>
        </div>
      </div>

    {/* <hr className="custom-hr mt-4"></hr> */}


      <footer className="bg-light text-center text-lg-start mt-5">
      <div className="container p-4">
        <div className="row">
          <div className="col-lg-4 col-md-6 mb-4 mb-md-0">
            <h5 className="text-uppercase">Contact Us</h5>
            <p>
              {/* Address: 123 Main St, Anytown, USA<br /> */}
              Mail: contact@medilink.com<br />
              Phone: +1 (555) 123-4567
            </p>
            {/* <a href="">
              <i class="fa fa-map-marker" aria-hidden="true"></i>
              <span>
                Address: 123 Main St, Anytown, USA<br />
              </span>
            </a>
            <a href="">
              <i class="fa fa-phone" aria-hidden="true"></i>
              <span>
                Phone: +1 (555) 123-4567
              </span>
            </a>
            <a href="">
              <i class="fa fa-envelope" aria-hidden="true"></i>
              <span>
                Mail: contact@medilink.com<br />
              </span>
            </a> */}
          </div>

          <div className="col-lg-4 col-md-6 mb-4 mb-md-0">
            <h5 className="text-uppercase">Follow Us</h5>
            <a href="https://www.facebook.com" className="me-3 text-dark icon-spacing" target="_blank" rel="noopener noreferrer">
              <i className="fab fa-facebook-f" ></i>
            </a>
            <a href="https://www.twitter.com" className="me-3 text-dark icon-spacing" target="_blank" rel="noopener noreferrer">
              <i className="fab fa-twitter"></i>
            </a>
            <a href="https://www.instagram.com" className="me-3 text-dark icon-spacing" target="_blank" rel="noopener noreferrer">
              <i className="fab fa-instagram"></i>
            </a>
            <a href="https://www.linkedin.com" className="me-3 text-dark icon-spacing" target="_blank" rel="noopener noreferrer">
              <i className="fab fa-linkedin-in"></i>
            </a>
          </div>

          <div className="col-lg-4 col-md-12 mb-4 mb-md-0">
            <h5 className="text-uppercase">Copyright</h5>
            <p>&copy; 2024 MediLink. All rights reserved.</p>
          </div>
        </div>
      </div>
    </footer>

      
    </div>
  );
};

export default DoctorAppointmentForm;
