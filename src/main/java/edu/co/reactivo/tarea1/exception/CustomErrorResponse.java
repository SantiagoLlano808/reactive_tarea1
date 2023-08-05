package edu.co.reactivo.tarea1.exception;

public record CustomErrorResponse(Integer status, String error, String message, Long timestamp) {
}
