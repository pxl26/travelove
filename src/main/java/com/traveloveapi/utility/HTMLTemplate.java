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
                    <h1 style="color: #333;">Xin chào""" +  (user_name==null ? "" : (" "+ user_name)) + """
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
                        <img src="{logoURL}" alt="" width="100" height="100" title="Logo" style="margin-bottom: 20px;">
                                
                        <h1 style="color: #333;">Chào mừng bạn đến với Travelove!</h1>
                                
                        <p style="margin-bottom: 20px;">Để hoàn tất việc đăng ký tài khoản của bạn, vui lòng nhập mã OTP sau:</p>
                                
                        <h2 style="font-size: 32px; font-weight: bold; color: #007bff; margin-bottom: 10px;">{OTP}</h2>
                                
                        <p style="color: #555;">Mã này có hiệu lực trong <span style="font-weight: bold;">3</span> phút.</p>
                                
                        <p style="color: #777; margin-top: 20px;">Nếu bạn không đăng ký tài khoản này, vui lòng bỏ qua email này.</p>
                    </div>
                </body>
                </html>
                """).replace("{logoURL}", "https://storage.travelovecompany.com/video/travelove_logo.jpg").replace("{OTP}", otp);
    }

    static public String successfulOwnerRegistration(String company_name, String url) {
        return ("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Registration Successful</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="background-color: #fff; padding: 40px; border-radius: 8px; text-align: center; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto;">
                                
                        <img src="{logo_URL}" alt="[Your Brand Name]" style="max-width: 200px; margin-bottom: 30px;">\s
                                
                        <h1 style="color: #333; font-size: 28px;">Chúc mừng, bạn đã đăng ký thành công!</h1>
                                
                        <p style="margin-bottom: 20px; font-size: 16px;">Chào mừng bạn đến với Travelove. Tài khoản chủ dịch vụ của bạn đã được tạo thành công.</p>
                                
                        <p style="margin-bottom: 20px; font-size: 16px;">Để tăng cường bảo mật, vui lòng thiết lập mật khẩu cho tài khoản của bạn bằng cách nhấp vào nút bên dưới:</p>
                                <a href="{new_pass_url}" style="display: inline-block; padding: 12px 24px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px; font-size: 16px;">Tạo mật khẩu mới</a>
                        <h2 style="color: #007bff; margin-bottom: 10px;">{company_name}</h2>
                                
                        <p style="color: #555; margin-bottom: 30px;">Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ của chúng tôi. Chúng tôi hy vọng bạn sẽ có những trải nghiệm tuyệt vời.</p>
                                                                
                        <p style="color: #777; margin-top: 30px;">Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi qua email: <a href="mailto:[Email hỗ trợ]">travelovecompany@gmail.com</a></p>
                    </div>
                </body>
                </html>
                """).replace("{company_name}", company_name).replace("{new_pass_url}", url).replace("{logo_URL}", "https://storage.travelovecompany.com/video/travelove_logo.jpg");
    }

    static public String rejectedOwnerRegistration(String company_name, String cause) {
        return ("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Registration Unsuccessful</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="background-color: #fff; padding: 40px; border-radius: 8px; text-align: center; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto;">
                                
                        <img src="{logoURL}" alt="traveloveLogo" style="max-width: 200px; margin-bottom: 30px;">\s
                                
                        <h1 style="color: #d9534f; font-size: 28px;">Đăng ký không thành công cho công ty {company_name}</h1>
                                
                        <p style="margin-bottom: 20px; font-size: 16px;">Chúng tôi rất tiếc phải thông báo rằng quá trình đăng ký tài khoản của bạn trên Travelove chưa thành công.</p>
                                
                        <h2 style="color: #333; margin-bottom: 10px;">Nguyên nhân:</h2>
                                
                        <p style="color: #555; margin-bottom: 30px;">{cause}</p>
                                
                        <p style="color: #555; margin-bottom: 30px;">
                            Vui lòng kiểm tra lại thông tin đã cung cấp và thử lại. Nếu bạn tiếp tục gặp sự cố, đừng ngần ngại liên hệ với chúng tôi để được hỗ trợ.
                        </p>
                                
                                
                        <p style="color: #777; margin-top: 30px;">Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi qua email: <a href="mailto:[Email hỗ trợ]">travelovecompany@gmail.com</a></p>
                    </div>
                </body>
                </html>
                """).replace("{logoURL}", "https://storage.travelovecompany.com/video/travelove_logo.jpg").replace("{cause}", cause).replace("{company_name}", company_name);
    }
}
