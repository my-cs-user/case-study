import React, { useEffect, useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import DynamicForm from './DynamicForm';

const EntityModal = ({ show, onHide, entity = {}, fields, masterEntities, onCreate, onUpdate, onDelete }) => {
    const [formData, setFormData] = useState(entity || {}); // Varsayılan değer ekle
    const [formErrors, setFormErrors] = useState({});

    useEffect(() => {
        setFormData(entity || {}); // Entity değiştiğinde form verisini güncelle
    }, [entity]);

    const handleFormChange = (updatedFormData) => {
        setFormData(updatedFormData); // Formdaki değişiklikleri güncelle
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
                onUpdate(formData); // Güncellenmiş veriyi gönder
            } else {
                onCreate(formData); // Yeni kayıt oluştur
            }
        } else {
            alert('Please fix the form errors before submitting');
        }
    };

    const handleDelete = () => {
        onDelete(formData); // Kaydı sil
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
                    masterEntities={masterEntities} // masterEntities'i geç
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
