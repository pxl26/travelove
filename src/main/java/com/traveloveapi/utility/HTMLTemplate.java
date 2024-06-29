package com.traveloveapi.utility;

public class HTMLTemplate {
    static public String resetPassword(String otp, String user_name) {
        return ("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>Xác minh mã OTP của bạn</title>
            </head>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="background-color: #fff; padding: 30px; border-radius: 8px; text-align: center; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">
                    <h1 style="color: #333;">Xin chào""" + " " +  user_name + """
                    ,</h1>
            
                    <p style="margin-bottom: 20px;">Mã OTP của bạn để xác minh là:</p>
            
                    <h2 style="font-size: 32px; font-weight: bold; color: #007bff; margin-bottom: 30px;">""" + otp + """
                    </h2>
           
                    <p style="color: #555;">Mã này có hiệu lực trong <span style="font-weight: bold;">3</span> phút.</p>
            
                    <p style="color: #777; margin-top: 20px;">Nếu bạn không yêu cầu mã OTP này, vui lòng bỏ qua email này.</p>
                </div>
            </body>
            </html>
            """);
    }

    static public String verifyEmailRegistration(String otp) {
        return ("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Xác nhận địa chỉ email của bạn</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="background-color: #fff; padding: 30px; border-radius: 8px; text-align: center; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">
                        <img src="{logoURL}" alt="Travelove logo" style="max-width: 150px; margin-bottom: 20px;">\s
                                
                        <h1 style="color: #333;">Chào mừng bạn đến với Travelove!</h1>
                                
                        <p style="margin-bottom: 20px;">Để hoàn tất việc đăng ký tài khoản của bạn, vui lòng nhập mã OTP sau:</p>
                                
                        <h2 style="font-size: 32px; font-weight: bold; color: #007bff; margin-bottom: 30px;">{OTP}</h2>
                                
                        <p style="color: #555;">Mã này có hiệu lực trong <span style="font-weight: bold;">[Thời gian hiệu lực]</span> phút.</p>
                                
                        <p style="color: #777; margin-top: 20px;">Nếu bạn không đăng ký tài khoản này, vui lòng bỏ qua email này.</p>
                    </div>
                </body>
                </html>
                """).replace("{logoURL}", "https://storage.travelovecompany.com/video/travelove_logo.svg").replace("{OTP}", otp);
    }
}
