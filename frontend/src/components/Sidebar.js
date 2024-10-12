import React from 'react';
import { Link } from 'react-router-dom';

function Sidebar() {
  return (
    <nav className="sidebar">
      <ul>
        <li><Link to="/departments">Departments & Employees</Link></li>
        <li><Link to="/courses">Courses & Students</Link></li>
      </ul>
    </nav>
  );
}

export default Sidebar;
