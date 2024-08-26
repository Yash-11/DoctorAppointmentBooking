// src/utils/authUtils.js
import { useNavigate } from 'react-router-dom';
import { useUser } from '../context/UserContext';

const useLogout = (setUser) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        setUser(null);
        navigate('/login');
    };

    return handleLogout;
};

export default useLogout;

// export const handleLogout = () => {
//     const { user, setUser } = useUser();
//     const navigate = useNavigate();

//     return () => {
//         setUser(null);
//         navigate('/login');
//     };
// };
