import React, { useEffect } from 'react';
import { Modal, Button } from 'react-bootstrap';
import DynamicForm from './DynamicForm';

const EntityModal = ({ show, onHide, entity = {}, fields, masterEntities, onCreate, onUpdate, onDelete }) => {
    const [formData, setFormData] = React.useState(entity || {}); // Varsayılan değer ekle

    useEffect(() => {
        setFormData(entity || {}); // Entity değiştiğinde form verisini güncelle
    }, [entity]);

    const handleFormChange = (updatedFormData) => {
        setFormData(updatedFormData); // Formdaki değişiklikleri güncelle
    };

    const handleSubmit = () => {
        if (formData.id) {
            onUpdate(formData); // Güncellenmiş veriyi gönder
        } else {
            onCreate(formData); // Yeni kayıt oluştur
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
