import React from "react";
import { Button, FormControl, Grid, InputAdornment, InputLabel, makeStyles, OutlinedInput, Typography } from "@material-ui/core";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
    red: {
        color: theme.palette.error.main
    },
    green: {
        color: theme.palette.success.main
    }
}));

const Manager = () => {
    const classes = useStyles();

    const [costPerCar, setCostPerCar] = React.useState("");

    const [turnover, setTurnover] = React.useState("?");
    const [taxes, setTaxes] = React.useState("?");
    const [turnoverAfterTax, setTurnoverAfterTax] = React.useState("?");
    const [cost, setCost] = React.useState("?");
    const [profit, setProfit] = React.useState("?");

    const handleCostInput = (event) => {
        if (event.target.value === "" || /^[0-9]+\.{0,1}[0-9]{0,2}$/.test(event.target.value)) {
            setCostPerCar(event.target.value);
        }
    }

    const getCalculation = () => {
        if (costPerCar !== "") {
            axios.post("./api/", `incomestatement,${costPerCar}`).then((response) => {
                setTurnover(response.data.turnover);
                setTaxes(response.data.taxes);
                setTurnoverAfterTax(response.data.turnoverAfterTax);
                setCost(response.data.cost);
                setProfit(response.data.profit);
            });
        }
    };

    return (
        <Grid
            container
            direction="row"
            justify="center"
            alignItems="center"
            spacing={2}
        >
            <Grid item xs={12}>
                <Typography variant="h3">GuV-Rechnung</Typography>
            </Grid>
            <Grid item sm={6}>
                <FormControl fullWidth variant="outlined">
                    <InputLabel htmlFor="cost-input">Kosten pro Fahrzeug</InputLabel>
                    <OutlinedInput
                        id="cost-input"
                        label="Kosten pro Fahrzeug"
                        endAdornment={<InputAdornment position="end">€</InputAdornment>}
                        labelWidth={70}
                        fullWidth
                        onChange={handleCostInput}
                        value={costPerCar}
                    />
                </FormControl>
            </Grid>
            <Grid item sm={6} />
            <Grid item sm={12}>
                <Button
                    color="primary"
                    size="large"
                    variant="contained"
                    onClick={getCalculation}
                >
                    Errechnen
                </Button>
            </Grid>
            <Grid item sm={12}>
                <Typography variant="h6">Umsatz: {turnover} €</Typography>
            </Grid>
            <Grid item sm={12}>
                <Typography className={classes.red} variant="h6">Steuern: {taxes} €</Typography>
            </Grid>
            <Grid item sm={12}>
                <Typography variant="h6">Rest: {turnoverAfterTax} €</Typography>
            </Grid>
            <Grid item sm={12}>
                <Typography className={classes.red} variant="h6">Kosten: {cost} €</Typography>
            </Grid>
            <Grid item sm={12}>
                <Typography className={classes.green} variant="h6">Profit: {profit} €</Typography>
            </Grid>
        </Grid>
    );
};

export default Manager;