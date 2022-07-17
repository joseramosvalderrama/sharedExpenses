import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './routes/App';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NewExpense from 'routes/NewExpense';
import NewPerson from 'routes/NewPerson';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<App/>}></Route>
      <Route path="person/new" element={<NewPerson/>}></Route>
      <Route path="expense/new" element={<NewExpense/>}></Route>
    </Routes>
  </BrowserRouter>
);
