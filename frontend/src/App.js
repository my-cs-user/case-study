import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import DepartmentsEmployees from './components/DepartmentsEmployees';
import CoursesStudents from './components/CoursesStudents';
import './App.css';

function App() {
  return (
    <Router>
      <div className="app">
        <Header />
        <div className="app-layout">
          <Sidebar />
          <div className="content">
            <Routes>
              <Route path="/departments" element={<DepartmentsEmployees />} />
              <Route path="/courses" element={<CoursesStudents />} />
            </Routes>
          </div>
        </div>
      </div>
    </Router>
  );
}

export default App;