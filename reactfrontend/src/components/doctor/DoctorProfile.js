import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Avatar from 'react-avatar';
import Badge from 'react-bootstrap/Badge';
import './DoctorProfile.css'
import { useParams } from 'react-router-dom';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

const DoctorProfile = () => {

    const _doctor = {
        id: 1,
        name: "Yash",  // Doctor's name
        email: "yash1@gmail.com",  // Email address
        specializations: ["ENT", "Dermatology"],  // List of specializations
        city: "Delhi",  // City where the doctor is located
        yearsOfExperience: 5,  // Years of experience
        address: "Delhi, India",  // Full address
        rating: 4.5,  // Doctor's rating
        description: "Experienced ENT and Dermatology specialist.",  // Short biography
        services: ["ENT Consultation", "Skin Care Treatments"],  // List of services provided
        educations: ["M.D. from Harvard Medical School",
            "Residency at Johns Hopkins Hospital",
            "Fellowship in Cardiology at Mayo Clinic"],  // List of degrees
        experiences: [
            "Chief of Cardiology at City Hospital (2015-present)",
            "Associate Professor of Medicine at State University (2010-2015)"
        ]
    };
    const { id } = useParams(); // Get doctor ID from the URL
    const [doctor, setDoctor] = useState(_doctor);




    // Fetch doctor details using the doctor ID
      useEffect(() => {
        const fetchDoctor = async () => {
          try {
            const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/doctors/${id}`); // Replace with your API endpoint
            console.log(response.data);

            setDoctor(response.data);
          } catch (error) {
            console.error('Error fetching doctor data', error);
          }
        };

        fetchDoctor();
      }, [id]);

    // Render loading state
    //   if (!doctor) {
    //     return <div>Loading doctor profile...</div>;
    //   }

    

    // console.log(doctor.educations.length);


    return (
        <div className='w-100 mx-auto px-4 py-8'>
            <div className='profile-card mb-8'>
                <div className='profile-card-content'>
                    <div className='flex flex-col md:flex-row items-center md:items-start gap-6'>

                        <div className="relative w-32 h-32">
                            <img
                                src={require('../../assets/profile.png')}
                                alt="dsf"
                                className="profile-img w-full h-full rounded-full object-cover"
                            />
                        </div>
                        <div>
                            <div className='text-3xl font-bold mb-2'>Dr. {doctor.name}</div>
                            <div className='flex flex-row gap-2 mb-1'>
                                {doctor.services.map((service, id) => {
                                    return <Badge bg="dark" text="light">{service}</Badge>
                                })}
                            </div>
                            <div className='flex flex-row'>

                                <i class="fa-regular fa-thumbs-up mr-1"></i>
                                <div className='text-sm'>{doctor.rating}%</div>
                            </div>
                            {/* <FontAwesomeIcon icon="fa-regular fa-thumbs-up" /> */}
                            <p className="text-muted-foreground mb-4">{doctor.description}</p>
                            <button type="button" class="appointment-btn">Book Appointment</button>

                        </div>
                    </div>
                </div>
            </div>

            <div className='services mb-6'>
                <div className='services-content px-4'>
                    <div className='text-2xl font-semibold mb-4'>Services</div>
                    <ul className='list-disc pl-5 space-y-2'>
                        {doctor.services.map((service, id) => {
                            return <li key={id}>{service}</li>
                        })}
                    </ul>
                </div>
            </div>

            <div className='flex flex-col md:flex-row gap-6'>
                <div className='education  w-100'>
                    <div className='education-content px-4'>
                        <div className='text-2xl font-semibold mb-4'>Education</div>
                        <ul className='list-disc pl-5 space-y-2'>
                            {doctor.educations.map((education, id) => {
                                return <li key={id}>{education}</li>
                            })}
                        </ul>
                    </div>
                </div>
                <div className='experience  w-100'>
                    <div className='experience-content px-4'>

                        <div className='text-2xl font-semibold mb-4'>Experience</div>

                        <ul className='list-disc pl-5 space-y-2'>
                            {doctor.experiences.map((education, id) => {
                                return <li key={id}>{education}</li>
                            })}
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        // <div className="doctor-profile">
        //   <div className="profile-header">
        //     <h2>{doctor.name}</h2>
        //     <p>
        //       {doctor.specialization.name} | {doctor.city.name}
        //     </p>
        //   </div>

        //   <div className="profile-details">
        //     <p><strong>Years of Experience:</strong> {doctor.yearsOfExperience} years</p>
        //     <p><strong>Qualification:</strong> {doctor.qualification}</p>
        //     <p><strong>Address:</strong> {doctor.address}</p>
        //     <p><strong>Rating:</strong> {doctor.rating} / 5</p>
        //     <p><strong>Description:</strong> {doctor.description}</p>
        //   </div>
        // </div>
    );
};

export default DoctorProfile;
