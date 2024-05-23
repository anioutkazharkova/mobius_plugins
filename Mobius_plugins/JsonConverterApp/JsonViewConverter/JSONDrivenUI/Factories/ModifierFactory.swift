//
//  ModifierFactory.swift
//  
//
//  Created by Enes Karaosman on 27.11.2020.
//

import SwiftUI

internal struct ModifierFactory {
    /// Applies Frame in case `width` & `height` is not nil.
    struct FrameModifier: ViewModifier {
        var width: CGFloat? = nil
        var height: CGFloat? = nil

        @ViewBuilder func body(content: Content) -> some View {
            content.frame(width: width, height: height)
        }
        
        var output: String {
            if let width = width, let height = height {
                return "\n.frame(width: \(width), height: \(height))"
            } else {
                return ""
            }
        }
    }
    
    /// Applies ForegroundColor in case `foregroundColor` is not nil.
    struct ForegroundModifier: ViewModifier {
        var foregroundColor: Color?
        
        var output: String {
            if let foregroundColor = foregroundColor {
                let name = foregroundColor.name ?? ".blue"
                return "\n.foregroundColor(Color.init(hex: \"\(foregroundColor)\"))"
            } else {
                return ""
            }
        }
        
        @ViewBuilder func body(content: Content) -> some View {
            if let foregroundColor {
                content.foregroundColor(foregroundColor)
            } else {
                content
            }
        }
    }
    
    /// Applies Padding for all edges in case `padding` is not nil.
    struct PaddingModifier: ViewModifier {
        var padding: CGFloat?
        
        var output: String {
            if let padding {
                return "\n.padding(\(padding))"
            } else {
                return ""
            }
        }
        
        @ViewBuilder func body(content: Content) -> some View {
            if let padding {
                content.padding(padding)
            } else {
                content
            }
        }
    }
    
    /// Applies Border in case `borderColor` & `borderWidth` is not nil.
    struct BorderModifier: ViewModifier {
        var borderColor: Color?
        var borderWidth: CGFloat?
        
        var output: String {
            if let borderWidth, let borderColor {
                return "\n.border(\(borderColor), width: \(borderWidth))"
            } else {
                return ""
            }
        }
        
        @ViewBuilder func body(content: Content) -> some View {
            if let borderWidth, let borderColor {
                content.border(borderColor, width: borderWidth)
            } else {
                content
            }
        }
    }
}
