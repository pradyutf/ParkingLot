package strategy;

import models.Ticket;
import java.util.Date;

public interface PricingStrategy {
    double calculateFee(Ticket ticket, Date exitTime);
}