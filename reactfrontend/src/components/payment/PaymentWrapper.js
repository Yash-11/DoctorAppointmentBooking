import React from 'react';
import { loadStripe } from '@stripe/stripe-js';
import { Elements, CardElement, useStripe, useElements } from '@stripe/react-stripe-js';
import { useLocation } from 'react-router-dom';
import './Payment.css';
import axios from 'axios';

const stripePromise = loadStripe('your-publishable-key-here');

// https://medium.com/@shaikhrezwan66/implementing-payment-system-in-your-react-app-using-stripe-7e0e3f984532

const Payment = () => {
  const stripe = useStripe();
  const elements = useElements();
  const location = useLocation();
  const { state } = location;
  const clientSecret = state?.clientSecret || '';
  const patientName = state?.patientName || '';

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!stripe || !elements) {
      return;
    }

    const cardElement = elements.getElement(CardElement);

    const paymentResult = await stripe.confirmCardPayment(clientSecret, {
      payment_method: {
        card: cardElement,
        billing_details: {
          name: patientName,
        },
      },
    });

    if (paymentResult.error) {
      alert(`Payment failed: ${paymentResult.error.message}`);
    } else {
      if (paymentResult.paymentIntent.status === 'succeeded') {
        alert('Payment successful!');
        // Optionally, navigate to a different page or reset the form
      }
    }
  };

  const createPaymentIntent = async () => {
    try {
      const response = await axios.post('/api/payment/create-payment-intent', {
        amount: 1000, // amount in cents
      });
      //setClientSecret(response.data.clientSecret);
    } catch (error) {
      console.error('Error creating payment intent', error);
    }
  };

  // Create a payment intent when the component mounts
  React.useEffect(() => {
    createPaymentIntent();
  }, []);

  return (
    <div className="payment-container">
      <div className="payment-box">
        <h2>Payment</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="card-element">Credit or debit card</label>
            <CardElement id="card-element" />
          </div>
          <button type="submit" className="btn btn-primary" disabled={!stripe}>
            Pay
          </button>
        </form>
      </div>
    </div>
  );
};

const PaymentWrapper = () => (
  <Elements stripe={stripePromise}>
    <Payment />
  </Elements>
);

export default PaymentWrapper;
