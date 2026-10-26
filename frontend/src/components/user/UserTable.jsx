import {
    IconButton,
    Stack,
    Chip,
    Tooltip,
} from "@mui/material";

import { DataGrid } from "@mui/x-data-grid";

import VisibilityIcon from "@mui/icons-material/Visibility";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

export default function UserTable({
    users,
    loading,
    onView,
    onEdit,
    onDelete,
}) {
    const columns = [
        {
            field: "firstName",
            headerName: "Name",
            flex: 1.2,
            renderCell: (params) => (
                <>
                    {params.row.firstName}{" "}
                    {params.row.lastName}
                </>
            ),
        },
        {
            field: "email",
            headerName: "Email",
            flex: 1.5,
        },
        {
            field: "organizationName",
            headerName: "Organization",
            flex: 1.2,
        },
        {
            field: "jobTitle",
            headerName: "Job Title",
            flex: 1,
        },
        {
            field: "active",
            headerName: "Status",
            width: 120,
            renderCell: (params) => (
                <Chip
                    label={
                        params.row.active
                            ? "Active"
                            : "Inactive"
                    }
                    color={
                        params.row.active
                            ? "success"
                            : "default"
                    }
                    size="small"
                />
            ),
        },
        {
            field: "actions",
            headerName: "Actions",
            width: 170,
            sortable: false,
            renderCell: (params) => (
                <Stack
                    direction="row"
                    spacing={1}
                >
                    <Tooltip title="View">
                        <IconButton
                            color="info"
                            onClick={() =>
                                onView(params.row)
                            }
                        >
                            <VisibilityIcon />
                        </IconButton>
                    </Tooltip>

                    <Tooltip title="Edit">
                        <IconButton
                            color="primary"
                            onClick={() =>
                                onEdit(params.row)
                            }
                        >
                            <EditIcon />
                        </IconButton>
                    </Tooltip>

                    <Tooltip title="Delete">
                        <IconButton
                            color="error"
                            onClick={() =>
                                onDelete(params.row)
                            }
                        >
                            <DeleteIcon />
                        </IconButton>
                    </Tooltip>
                </Stack>
            ),
        },
    ];

    return (
        <DataGrid
            rows={users}
            columns={columns}
            loading={loading}
            getRowId={(row) => row.id}
            pageSizeOptions={[5, 10, 20]}
            initialState={{
                pagination: {
                    paginationModel: {
                        pageSize: 5,
                    },
                },
            }}
            disableRowSelectionOnClick
            autoHeight
        />
    );
}