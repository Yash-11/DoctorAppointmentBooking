import { Navigate } from 'react-router-dom';
import { useContext } from 'react';

import { useUser } from './UserContext'; 

const AuthRoute = ({role,  children }) => {
//   const { user } = useContext(UserContext); // Assuming you have a user context to manage authentication
  const { user } = useUser();
  if (!user) {
    return <Navigate to="/login" />;
  }

  if (user.role !== role) {
    return <Navigate to="/login" />;
  }

  return children;
};

export default AuthRoute;
