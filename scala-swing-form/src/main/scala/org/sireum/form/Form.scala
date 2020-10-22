//package org.sireum.form
//
//import java.awt.{Color, Dimension}
//
//import javax.swing.{BorderFactory, BoxLayout, JButton, JComponent, JFrame, JLabel, JPanel, JSeparator, SwingConstants}
//import org.sireum.form.Form.{staticCounter, threadLocalCounter}
//
//object Form {
//  private var staticCounter = 0
//  private val threadLocalCounter = new ThreadLocal[Integer] // use Integer to make boxing clear
//}
//
//class Form {
//
//  private val frame = new JFrame()
//  private val panel = new JPanel()
//
//  private var instanceCounter = 0
//
//  private val instanceLabel    = new JLabel("instance:     0")
//  private val threadLocalLabel = new JLabel("thread local: 0")
//  private val staticLabel      = new JLabel("static:       0")
//
//  private val button = new JButton()
//  button.setText("Click me!")
//
//  button.addActionListener(action => {
//    instanceCounter += 1
//    staticCounter += 1
//
//    var tl = threadLocalCounter.get()
//    if (tl == null) { tl = 0 }
//    tl += 1
//    threadLocalCounter.set(tl)
//
//    instanceLabel.setText(s"instance:     $instanceCounter")
//    threadLocalLabel.setText(s"thread local:     $tl")
//    staticLabel.setText(s"static:     $staticCounter")
//
//  })
//
//  panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS))
//  panel.add(instanceLabel)
//  panel.add(new JSeparator(SwingConstants.HORIZONTAL))
//  panel.add(threadLocalLabel)
//  panel.add(new JSeparator(SwingConstants.HORIZONTAL))
//  panel.add(staticLabel)
//  panel.add(new JSeparator(SwingConstants.HORIZONTAL))
//  panel.add(button)
//  frame.setPreferredSize(new Dimension(480, 360))
//
//  frame.add(panel)
//  frame.pack()
//  frame.setVisible(true)
//
//}
