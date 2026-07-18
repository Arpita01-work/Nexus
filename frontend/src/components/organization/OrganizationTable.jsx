import { DataGrid } from "@mui/x-data-grid";
import {
  IconButton,
  Stack,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

export default function OrganizationTable({
  organizations,
  loading,
  onEdit,
  onDelete,
}) {
  const columns = [
  {
    field: "name",
    headerName: "Organization",
    flex: 1.5,
  },
  {
    field: "code",
    headerName: "Code",
    width: 120,
  },
  {
    field: "email",
    headerName: "Email",
    flex: 1.5,
  },
  {
    field: "phone",
    headerName: "Phone",
    width: 150,
  },
  {
    field: "website",
    headerName: "Website",
    flex: 1.5,
  },
  {
    field: "actions",
    headerName: "Actions",
    width: 120,
    sortable: false,
    renderCell: (params) => (
      <Stack direction="row" spacing={1}>
        <IconButton color="primary" onClick={() => onEdit(params.row)}>
          <EditIcon />
        </IconButton>

        <IconButton color="error" onClick={() => onDelete(params.row)}>
          <DeleteIcon />
        </IconButton>
      </Stack>
    ),
  },
];

  return (
    <DataGrid
      rows={organizations}
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