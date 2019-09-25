package com.kiroule.vaadin.businessapp.backend;

import java.time.LocalDate;

public class Invoice {

	private final Long id;
	private final Status status;
	private final Order order;
	private final LocalDate invoiceDate;
	private final LocalDate dueDate;

	public enum Status {
		OUTSTANDING("Outstanding"), OPEN("Open"), PAID("Paid"), OVERDUE(
				"Overdue");

		private String name;

		Status(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public Invoice(Long id, Status status, Order order, LocalDate invoiceDate,
	               LocalDate dueDate) {
		this.id = id;
		this.status = status;
		this.order = order;
		this.invoiceDate = invoiceDate;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public Order getOrder() {
		return order;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
}
