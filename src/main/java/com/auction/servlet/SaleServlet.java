package com.auction.servlet;

import com.auction.ejb.SaleControllerBean;
import com.auction.ejb.OfferProcessorBean;
import com.auction.model.Sale;
import com.auction.util.UserSessionManager;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/sale")
public class SaleServlet extends HttpServlet {
    @EJB
    private SaleControllerBean saleController;

    @EJB
    private OfferProcessorBean offerProcessor;

    @Inject
    private UserSessionManager sessionManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!sessionManager.isLoggedIn()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("sales", saleController.getOngoingSales());
        req.setAttribute("loggedInUser", sessionManager.getLoggedInUser());
        req.getRequestDispatcher("/sale.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!sessionManager.isLoggedIn()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if ("create".equals(action)) {
            String itemName = req.getParameter("itemName");
            double startPrice = Double.parseDouble(req.getParameter("startBid"));
            String endTimeStr = req.getParameter("endTime");
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr.replace(" ", "T"));
            saleController.initiateSale(itemName, startPrice, endTime);
            resp.sendRedirect("sale");
        } else if ("offer".equals(action)) {
            Long saleId = Long.parseLong(req.getParameter("saleId"));
            String bidder = sessionManager.getLoggedInUser(); // Use logged-in user as bidder
            double amount = Double.parseDouble(req.getParameter("amount"));

            try {
                boolean success = offerProcessor.submitOffer(saleId, bidder, amount);
                if (success) {
                    resp.sendRedirect("sale");
                } else {
                    req.setAttribute("error", "Failed to submit offer: Invalid sale or offer amount too low.");
                    doGet(req, resp);
                }
            } catch (RuntimeException e) {
                req.setAttribute("s", "Failed to submit offer due to a system error. Please try again later.");
                doGet(req, resp);
            }
        }
    }
}