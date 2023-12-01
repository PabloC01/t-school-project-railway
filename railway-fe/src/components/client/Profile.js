import React, { useEffect, useState } from "react"
import { useAuth } from '../context/AuthContext'
import { Navigate } from "react-router-dom"
import { Table } from 'antd';

function Profile() {
    const Auth = useAuth()
    const user = Auth.getUser()

    const [username, setUsername] = useState()
    const [name, setName] = useState()
    const [surname, setSurname] = useState()
    const [birthDate, setBirthDate] = useState()

    const [ticketData, setTicketData] = useState()

    useEffect(() => {
        if(!username){
            fetch(`${process.env.REACT_APP_URL}/api/v1/clients/user?token=${user.token}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + user.token
                },
            })
            .then(response => {
                if(response.ok){
                    response.json()
                    .then(json => {
                        setUsername(json.username)
                        setName(json.name)
                        setSurname(json.surname)
                        setBirthDate(json.birthDate)
                    });
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")
        }

        if(!ticketData){
            fetch(`${process.env.REACT_APP_URL}/api/v1/clients/tickets?token=${user.token}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + user.token
                },
            })
            .then(response => {
                if(response.ok){
                    response.json()
                    .then(json => {
                        let values = []
                        for(const ticket of json){
                            values.push({
                                key: ticket.ticketId,
                                train: ticket.seat.trainNumber,
                                wagon: ticket.seat.wagonNumber,
                                seat: ticket.seat.number,
                                startStation: ticket.schedule.startStation.name,
                                endStation: ticket.schedule.endStation.name,
                                departure: ticket.schedule.departureTime.replace("T", " "),
                                arrival: ticket.schedule.arrivalTime.replace("T", " ")
                            })
                        }
                        setTicketData(values)
                    });
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")
        }
    });

    const columns = [
        {
            title: 'Train',
            dataIndex: 'train',
            key: 'train',
        },
        {
            title: 'Wagon',
            dataIndex: 'wagon',
            key: 'wagon',
        },
        {
            title: 'Seat Number',
            dataIndex: 'seat',
            key: 'seat',
        },
        {
            title: 'Start station',
            dataIndex: 'startStation',
            key: 'startStation',
        },
        {
            title: 'End station',
            dataIndex: 'endStation',
            key: 'endStation',
        },
        {
            title: 'Departure',
            dataIndex: 'departure',
            key: 'departure',
            sorter: (a, b) => Date.parse(a.departure) - Date.parse(b.departure),
            sortDirections: ['ascend', 'descend', 'ascend'],
        },
        {
            title: 'Arrival',
            dataIndex: 'arrival',
            key: 'arrival',
            sorter: (a, b) => Date.parse(a.arrival) - Date.parse(b.arrival),
            sortDirections: ['ascend', 'descend', 'ascend'],
        },
    ];

    if(!(user && user.role === 'C')){
        return <Navigate to="/"/>
    }

    return (
        <div>
            <div style={{position:"absolute", width: "100%", height: "94vh", minHeight:"900px", backgroundImage: "url(/home_bg.jpg)", backgroundRepeat: "no-repeat", backgroundSize: "cover", backgroundAttachment: "fixed", filter: "blur(6px)"}}></div>
            <div className="container py-5" style={{filter: "blur(0px)"}}>
                <div className="row justify-content-md-center">
                    <div className="col-lg-6">
                        <div className="card mb-4">
                            <div className="card-body">
                                <h3>Client Information</h3>
                                <div className="row">
                                    <div className="col-sm-6">
                                        <p className="mb-0">Username:</p>
                                    </div>
                                    <div className="col-sm-6">
                                        <p className="text-muted mb-0">{username}</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-sm-6">
                                        <p className="mb-0">Name:</p>
                                    </div>
                                    <div className="col-sm-6">
                                        <p className="text-muted mb-0">{name}</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-sm-6">
                                        <p className="mb-0">Surname:</p>
                                    </div>
                                    <div className="col-sm-6">
                                        <p className="text-muted mb-0">{surname}</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-sm-6">
                                        <p className="mb-0">Birth Date:</p>
                                    </div>
                                    <div className="col-sm-6">
                                        <p className="text-muted mb-0">{birthDate}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <h3>Tickets</h3>
                        <div className="container py-5 border rounded-3" style={{backgroundColor: "#FFFFFF"}}>
                        <Table bordered columns={columns} dataSource={ticketData} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Profile