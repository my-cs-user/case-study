export const employeeFields = [
    { name: 'name', label: 'Name', type: 'text', required: true },
    { name: 'surname', label: 'Surname', type: 'text', required: true },
    { name: 'email', label: 'Email', type: 'email', required: true },
    { name: 'phone', label: 'Phone', type: 'phone', required: true },
    { name: 'salary', label: 'Salary', type: 'number', required: true },
    { name: 'department', label: 'Department', type: 'select', required: true },
];

export const studentFields = [
    { label: 'First Name', name: 'name', type: 'text', required: true },
    { label: 'Last Name', name: 'surname', type: 'text', required: true },
    { label: 'Email', name: 'email', type: 'email', required: true },
    { label: 'Phone', name: 'phone', type: 'phone', required: true },
    { label: 'Courses', name: 'courses', type: 'select', multiple: true, required: true },
];
