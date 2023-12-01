import React, { useEffect, useState, useRef } from "react"
import { Navigate, useNavigate } from "react-router-dom"
import { useAuth } from "../context/AuthContext"
import { Select, Table, Tabs, Button, Input, Space } from 'antd'
import { SearchOutlined } from '@ant-design/icons'

function ClientPage() {
    const Auth = useAuth()
    const user = Auth.getUser()

    const [displayStationTable, setDisplayStationTable] = useState(false)
    const [displaySearchTable, setDisplaySearchTable] = useState(false)

    const [stationNames, setStationNames] = useState()
    const [selectValues, setSelectValues] = useState()
    const [startSelectValues, setStartSelectValues] = useState()
    const [endSelectValues, setEndSelectValues] = useState()
    const [filterValues, setFilterValues] = useState()

    const [station, setStation] = useState()
    const [stationSearchData, setStationSearchData] = useState()

    const [startStation, setStartStation] = useState()
    const [endStation, setEndStation] = useState()
    const [startTime, setStartTime] = useState()
    const [endTime, setEndTime] = useState()
    const [searchData, setSearchData] = useState()

    const [searchTimeError, setSearchTimeError] = useState(false)

    const navigate = useNavigate()
    
    useEffect(() => {
        if(!stationNames && user){
            fetch(`${process.env.REACT_APP_URL}/api/v1/clients/station_names`, {
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
                        setStationNames(json)
                        let values = json.map((station) => ({value: station, label: station}))
                        setSelectValues(values)
                    });
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")
        }
    })

    const loadSchedules = () => {
        if(station){
            fetch(`${process.env.REACT_APP_URL}/api/v1/clients/schedule?station_name=${station}`, {
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
                        setStationSearchData(getValues(json))
                        let values = []
                        for(const stationName of stationNames){
                            if(stationName !== station){
                                values.push({
                                    text: stationName,
                                    value: stationName,
                                })
                            }
                        }
                        setFilterValues(values)
                    });
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")

            setDisplayStationTable(true)
        }
    }

    const loadSearch = () => {
        if(startStation && endStation){
            let url = ""
            if((startTime && !endTime) || (!startTime && endTime)){
                return
            }
            else if(!startTime && !endTime){
                let now = new Date()
                let customStartTime = now.toJSON().slice(0,-1)
                
                now.setFullYear(now.getFullYear() + 1)
                let customEndTime = now.toJSON().slice(0,-1)

                url = `${process.env.REACT_APP_URL}/api/v1/clients/search_schedule?start_station=${startStation}&end_station=${endStation}&start_time=${customStartTime}&end_time=${customEndTime}`
            }
            else if(Date.parse(startTime) > Date.parse(endTime)){
                setSearchTimeError(true)
                return
            }
            else {
                url = `${process.env.REACT_APP_URL}/api/v1/clients/search_schedule?start_station=${startStation}&end_station=${endStation}&start_time=${startTime}&end_time=${endTime}`
            }

            fetch(url, {
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
                        setSearchData(getValues(json))
                    });
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")

            setDisplaySearchTable(true)
            setSearchTimeError(false)
        }
    }

    const getValues = (json) => {
        let values = json.map((schedule) => ({
            key: schedule.scheduleId,
            train: schedule.train.number,
            startStation: schedule.startStation.name,
            endStation: schedule.endStation.name,
            departure: schedule.departureTime.replace("T", " "),
            arrival: schedule.arrivalTime.replace("T", " ")
        }))

        return values
    }

    useEffect(() => {
        if(stationNames){
            let values = []
            for(const station of stationNames){
                if(station !== startStation){
                    values.push({
                        value: station,
                        label: station,
                    })
                }
            }
            setEndSelectValues(values)
        }
    },[startStation, stationNames]);

    useEffect(() => {
        if(stationNames){
            let values = []
            for(const station of stationNames){
                if(station !== endStation){
                    values.push({
                        value: station,
                        label: station,
                    })
                }
            }
            setStartSelectValues(values)
        }
    },[endStation, stationNames]);

    const timeErrorStyle = () => {
        return searchTimeError ? { "display": "block" } : { "display": "none" }
    }

    const searchInput = useRef(null);
    const getColumnSearchProps = (dataIndex) => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters, close }) => (
        <div
            style={{
            padding: 8,
            }}
            onKeyDown={(e) => e.stopPropagation()}
        >
            <Input
            ref={searchInput}
            placeholder={`Search ${dataIndex}`}
            value={selectedKeys[0]}
            onChange={(e) => setSelectedKeys(e.target.value ? [e.target.value] : [])}
            onPressEnter={() => confirm()}
            style={{
                marginBottom: 8,
                display: 'block',
            }}
            />
            <Space>
            <Button
                type="primary"
                onClick={() => confirm()}
                icon={<SearchOutlined />}
                size="small"
                style={{
                width: 90,
                }}
            >
                Search
            </Button>
            <Button
                onClick={() => clearFilters && clearFilters()}
                size="small"
                style={{
                width: 90,
                }}
            >
                Reset
            </Button>
            </Space>
        </div>
        ),
        filterIcon: (filtered) => (
        <SearchOutlined
            style={{
            color: filtered ? '#1677ff' : undefined,
            }}
        />
        ),
        onFilter: (value, record) =>
        record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownOpenChange: (visible) => {
        if (visible) {
            setTimeout(() => searchInput.current?.select(), 100);
        }
        },
    });

    const filterOption = (input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase());

    const stationSearchColumns = [
        {
            title: 'Train',
            dataIndex: 'train',
            key: 'train',
            ...getColumnSearchProps('train'),
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
            filters: filterValues,
            onFilter: (value, record) => record.endStation.indexOf(value) === 0,
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
        {
            title: '',
            key: 'action',
            render: (_, record) => (
                Date.parse(record.departure) > new Date() ? <button type="button" className="btn btn-link" onClick={()=>{navigate('/buy-ticket',{state:record})}}>Buy Ticket</button>
                                                        : <span className="text text-danger">Not available</span>
            ),
        },
    ]

    const searchColumns = [
        {
            title: 'Train',
            dataIndex: 'train',
            key: 'train',
            ...getColumnSearchProps('train'),
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
        {
            title: '',
            key: 'action',
            render: (_, record) => (
                Date.parse(record.departure) > new Date() ? <button type="button" className="btn btn-link" onClick={()=>{navigate('/buy-ticket',{state:record})}}>Buy Ticket</button>
                                                        : <span className="text text-danger">Not available</span>
            ),
        },
    ]

    if(!(user && user.role === 'C')){
        return <Navigate to="/"/>
    }

    return (
        <div>
            <div style={{position:"absolute", width: "100%", height: "94vh", minHeight:"900px", backgroundImage: "url(/home_bg.jpg)", backgroundRepeat: "no-repeat", backgroundSize: "cover", backgroundAttachment: "fixed", filter: "blur(6px)"}}></div>
            <Tabs
                defaultActiveKey="1"
                centered
                size="large"
                tabBarStyle={{backgroundColor:"#FFFFFF"}}
                items={[{
                    label: 'View Station Schedules',
                    key: 1,
                    children: 
                    <div className="container pt-2">
                        <div className="row row-cols-auto justify-content-md-center">
                            <h3>Choose a station to view the associated schedules</h3>
                            <Select
                                showSearch
                                placeholder="Station name"
                                optionFilterProp="children"
                                onChange={value => setStation(value)}
                                filterOption={filterOption}
                                size="large"
                                options={selectValues}
                            />
                            <button className="btn btn-large btn-dark" onClick={loadSchedules}>Search</button>
                        </div>
                        <div className="row row-cols-auto justify-content-md-center pt-5">
                            <div className="border rounded-3 pt-5 pb-2" style={{display: displayStationTable ? "block" : "none", backgroundColor:"#FFFFFF"}}>
                                <Table bordered columns={stationSearchColumns} dataSource={stationSearchData} />
                            </div>
                        </div>
                    </div>
                }, 
                {
                    label: 'Search schedule',
                    key: 2,
                    children:
                    <div>
                        <div className="container-fluid pt-2">
                            <div className="row row-cols-auto justify-content-md-center">
                                <h3>Search a schedule</h3>
                            </div>
                            <div className="row row-cols-auto justify-content-md-center pt-4">
                                <h4>Start Station:</h4>
                                <Select
                                    showSearch
                                    placeholder="Station name"
                                    optionFilterProp="children"
                                    onChange={value => setStartStation(value)}
                                    filterOption={filterOption}
                                    size="large"
                                    options={startSelectValues}
                                />
                                <h4>End Station:</h4>
                                <Select
                                    showSearch
                                    placeholder="Station name"
                                    optionFilterProp="children"
                                    onChange={value => setEndStation(value)}
                                    filterOption={filterOption}
                                    size="large"
                                    options={endSelectValues}
                                />
                                <h4>Time interval:</h4>
                                <input type="datetime-local" onChange={e => setStartTime(e.target.value)}/>
                                <h4>to</h4>
                                <input type="datetime-local" onChange={e => setEndTime(e.target.value)}/>
                                <div className="ps-4">
                                <button className="btn btn-large btn-dark" onClick={loadSearch}>Search</button>
                                </div>
                            </div>
                            <div className="row row-cols-auto justify-content-md-center pt-5">
                                <div className="border rounded-3 pt-5 pb-2" style={{display: displaySearchTable ? "block" : "none", backgroundColor:"#FFFFFF"}}>
                                    <Table bordered columns={searchColumns} dataSource={searchData} />
                                </div>
                            </div>
                        </div>
                        <div className="container text-center py-5" style={timeErrorStyle()}>
                            <div className="row justify-content-md-center">
                                <div className="col-sm-auto">
                                    <h5>The start time in the time interval is after the end time.</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                }]}
            />
        </div>
    )
}

export default ClientPage