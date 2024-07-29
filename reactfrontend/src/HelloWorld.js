import React, { useEffect, useState } from 'react';

function HelloWorld() {
    const [message, setMessage] = useState('');

    useEffect(() => {
        fetch('/api/hello')
            .then(response => response.text())
            .then(data => setMessage(data));
    }, []);

    return (
        <div>
            <h1>"sfef"</h1>
            <h3>{message}</h3>
        </div>
    );
}

export default HelloWorld;
