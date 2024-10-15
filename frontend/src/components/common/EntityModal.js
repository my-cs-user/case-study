import React, {useEffect, useState} from 'react';
import {Button, Modal} from 'react-bootstrap';
import DynamicForm from './DynamicForm';

const EntityModal = ({show, onHide, entity = {}, fields, masterEntities, onCreate, onUpdate, onDelete}) => {
    const [formData, setFormData] = useState(entity || {});
    const [formErrors, setFormErrors] = useState({});

    useEffect(() => {
        setFormData(entity || {});
    }, [entity]);

    const handleFormChange = (updatedFormData) => {
        setFormData(updatedFormData);
    };

    const validateForm = () => {
        return Object.keys(formErrors).every(key => !formErrors[key]) && Object.keys(fields).every(key => {
            const field = fields[key];
            return !(field.required && !formData[field.name]);
        });
    };

    const handleSubmit = () => {
        if (validateForm()) {
            if (formData.id) {
                onUpdate(formData);
            } else {
                onCreate(formData);
            }
        } else {
            alert('Please fix the form errors before submitting');
        }
    };

    const handleDelete = () => {
        onDelete(formData);
    };

    return (
        <Modal show={show} onHide={onHide}>
            <Modal.Header closeButton>
                <Modal.Title>{formData.id ? 'Update Employee' : 'Create Employee'}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <DynamicForm
                    fields={fields}
                    entity={formData}
                    masterEntities={masterEntities}
                    onChange={handleFormChange}
                    setErrors={setFormErrors}
                />
            </Modal.Body>
            <Modal.Footer>
                {formData.id ? (
                    <>
                        <Button variant="danger" onClick={handleDelete}>
                            Delete
                        </Button>
                        <Button variant="success" onClick={handleSubmit}>
                            Update
                        </Button>
                    </>
                ) : (
                    <Button variant="primary" onClick={handleSubmit}>
                        Create
                    </Button>
                )}
            </Modal.Footer>
        </Modal>
    );
};

export default EntityModal;
