import {
    Card,
    CardContent,
    Box,
    Typography,
} from "@mui/material";

export default function StatCard({
    title,
    value,
    icon,
    description,
}) {

    return (
        <Card
            elevation={1}
            sx={{
                height: "100%",
                borderRadius: 3,
            }}
        >
            <CardContent>

                <Box
                    sx={{
                        display: "flex",
                        justifyContent:
                            "space-between",
                        alignItems: "flex-start",
                    }}
                >

                    <Box>

                        <Typography
                            variant="body2"
                            color="text.secondary"
                            mb={1}
                        >
                            {title}
                        </Typography>

                        <Typography
                            variant="h4"
                            fontWeight={700}
                        >
                            {value}
                        </Typography>

                        {description && (
                            <Typography
                                variant="caption"
                                color="text.secondary"
                            >
                                {description}
                            </Typography>
                        )}

                    </Box>

                    {icon && (
                        <Box
                            sx={{
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "center",
                                width: 44,
                                height: 44,
                                borderRadius: 2,
                                backgroundColor:
                                    "#f1f5f9",
                            }}
                        >
                            {icon}
                        </Box>
                    )}

                </Box>

            </CardContent>
        </Card>
    );
}